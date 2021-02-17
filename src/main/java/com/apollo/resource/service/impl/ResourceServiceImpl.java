package com.apollo.resource.service.impl;

import com.apollo.resource.kafka.KafkaService;
import com.apollo.resource.model.Resource;
import com.apollo.resource.model.SharableResource;
import com.apollo.resource.service.ResourceService;
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
public class ResourceServiceImpl implements ResourceService {

    private final InteractiveQueryService interactiveQueryService;
    private final KafkaService kafkaService;
    @Value("${resource.kafka.store}")
    private String resourceStateStoreName;
    private ReadOnlyKeyValueStore<String, Resource> resourceStateStore;


    private ReadOnlyKeyValueStore<String, Resource> getResourceStateStore() {
        if (this.resourceStateStore == null)
            this.resourceStateStore = interactiveQueryService.getQueryableStore(this.resourceStateStoreName , QueryableStoreTypes.keyValueStore());
        return this.resourceStateStore;
    }


    @Override
    public Mono<Optional<Resource>> getResourceByID(String resourceId) {
        return Mono.just(Optional.ofNullable(this.getResourceStateStore().get(resourceId)));
    }

    @Override
    public Mono<Resource> createResource(Mono<Resource> resourceMono) {
        return this.kafkaService.sendResourceRecord(resourceMono).map(Optional::get);
    }

    @Override
    public Mono<Boolean> updateResource(Mono<Resource> resourceMono) {
        return resourceMono.flatMap(resource -> {
            Optional<Resource> resourceOptional = Optional.ofNullable(this.resourceStateStore.get(resource.getResourceId()));
            if (resourceOptional.isEmpty()) return Mono.empty();
            Resource updatedResource = resourceOptional.get();
            if (!updatedResource.getIsActive()) return Mono.empty();
            updatedResource.setResourceName(resource.getResourceName());
            updatedResource.setIsActive(resource.getIsActive());
            updatedResource.setIsPublic(resource.getIsPublic());
            updatedResource.setResourcesOwnerId(resource.getResourcesOwnerId());
            updatedResource.setResourceUrl(resource.getResourceUrl());
            return this.kafkaService.sendResourceRecord(resourceMono).map(Optional::isPresent);
        });
    }

    @Override
    public Mono<Boolean> shareResource(Mono<SharableResource> sharableResourceMono , Boolean flag) {
        return sharableResourceMono.flatMap(shareResource -> {
            Optional<Resource> resourceOptional = Optional.ofNullable(this.getResourceStateStore().get(shareResource.getResourceId()));
            if (resourceOptional.isEmpty()) return Mono.just(false);
            return Mono.just(resourceOptional.get()).flatMap(updatedResource -> {
                if (flag) updatedResource.removeResourceViewer(shareResource.getUserId());
                else updatedResource.addResourceViewer(shareResource.getUserId());
                return this.kafkaService.sendResourceRecord(Mono.just(updatedResource)).map(Optional::isPresent);
            });
        });
    }

    @Override
    public Mono<Boolean> deleteResource(String resourceId) {
        Optional<Resource> resourceOptional = Optional.ofNullable(this.getResourceStateStore().get(resourceId));
        if (resourceOptional.isEmpty()) return Mono.empty();
        Resource resource = resourceOptional.get();
        resource.setIsActive(false);
        return this.kafkaService.sendResourceRecord(Mono.just(resource)).map(Optional::isPresent);
    }
}
