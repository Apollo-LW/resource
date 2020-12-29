package com.apollo.resource.service;

import com.apollo.resource.kafka.KafkaService;
import com.apollo.resource.model.Resource;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ResourceServiceImpl implements ResourceService{

   @Value("${resource.kafka.store}")
   private String resourceStateStoreName;
   private final InteractiveQueryService interactiveQueryService;
   private final KafkaService kafkaService;
   private ReadOnlyKeyValueStore<String, Resource> resourceStateStore;


    private ReadOnlyKeyValueStore<String, Resource> getResourceStateStore() {
      if (this.resourceStateStore == null)
         this.resourceStateStore = interactiveQueryService.getQueryableStore(this.resourceStateStoreName , QueryableStoreTypes.keyValueStore());
      return this.resourceStateStore;
   }


    @Override
   public Mono<Resource> postResource(Mono<Resource> resourceMono) {
      return this.kafkaService.sendResourceRecord(resourceMono).map(Optional::get);
   }

   @Override
   public Mono<Boolean> updateResource(Mono<Resource> resourceMono) {
     return resourceMono.flatMap(resource -> {
         Optional<Resource> resourceOptional = Optional.ofNullable(this.resourceStateStore.get(resource.getResourceId()));
                if(resourceOptional.isEmpty()) return Mono.empty();
                Resource updatedResource = resourceOptional.get();
                if(!updatedResource.isActive()) return Mono.empty();
                updatedResource.setResourceName(resource.getResourceName());
                updatedResource.setActive(resource.isActive());
                updatedResource.setPublic(resource.isPublic());
                updatedResource.setResourcesOwnerId(resource.getResourcesOwnerId());
                updatedResource.setResourceUrl(resource.getResourceUrl());
                return this.kafkaService.sendResourceRecord(resourceMono).map(Optional::isPresent);
     });
   }

   @Override
     public Mono<Boolean> shareResourceFlag(String ownerId, String resourceId, boolean flag) {

               return null;
   }

   @Override
   public Mono<Boolean> deleteResource(String resourceId) {
      Optional<Resource> resourceOptional = Optional.ofNullable(this.getResourceStateStore().get(resourceId));
      if(resourceOptional.isEmpty()) return Mono.empty();
      Resource resource = resourceOptional.get();
      resource.setActive(false);
      return this.kafkaService.sendResourceRecord(Mono.just(resource)).map(Optional::isPresent);
   }





}