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

    @Value("${resource.kafka.store}")
    private String resourceStateStoreName;
    private final KafkaService kafkaService;
    private final InteractiveQueryService interactiveQueryService;
    private ReadOnlyKeyValueStore<String, Resource> resourceStateStore;

    private ReadOnlyKeyValueStore<String, Resource> getResourceStateStore() {
        if (this.resourceStateStore == null)
            this.resourceStateStore = interactiveQueryService.getQueryableStore(this.resourceStateStoreName , QueryableStoreTypes.keyValueStore());
        return this.resourceStateStore;
    }

    private Boolean isNotValid(final Optional<Resource> optionalResource , final String ownerId) {
        return optionalResource.isEmpty() || !optionalResource.get().getIsActive() || optionalResource.get().doesNotHaveOwner(ownerId);
    }

    @Override
    public Mono<Optional<Resource>> getResourceById(final String resourceId) {
        return Mono.just(Optional.ofNullable(this.getResourceStateStore().get(resourceId)));
    }

    @Override
    public Mono<Optional<Resource>> saveResource(final Mono<Resource> resourceMono) {
        return this.kafkaService.sendResourceRecord(resourceMono);
    }

    @Override
    public Mono<Boolean> updateResource(final Mono<Resource> resourceMono , final String ownerId) {
        return resourceMono.flatMap(resource -> this.getResourceById(resource.getResourceId()).flatMap(resourceOptional -> {
            if (this.isNotValid(resourceOptional , ownerId)) return Mono.just(false);
            Resource updatedResource = resourceOptional.get();
            updatedResource.setIsActive(resource.getIsActive());
            updatedResource.setIsPublic(resource.getIsPublic());
            updatedResource.setResourceUrl(resource.getResourceUrl());
            updatedResource.setResourceName(resource.getResourceName());
            return this.kafkaService.sendResourceRecord(resourceMono).map(Optional::isPresent);
        }));
    }

    @Override
    public Mono<Boolean> shareResource(final Mono<SharableResource> sharableResourceMono , final Boolean isAdd) {
        return sharableResourceMono.flatMap(sharableResource -> this.getResourceById(sharableResource.getResourceId()).flatMap(resourceOptional -> {
            if (this.isNotValid(resourceOptional , sharableResource.getOwnerId())) return Mono.just(false);
            Resource resource = resourceOptional.get();
            Boolean isShared;
            if (isAdd) isShared = resource.addResourceViewers(sharableResource.getUserIds());
            else isShared = resource.removeResourceViewers(sharableResource.getUserIds());
            return this.kafkaService.sendResourceRecord(Mono.just(resource)).map(sharedResource -> sharedResource.isPresent() && isShared);
        }));
    }

    @Override
    public Mono<Boolean> deleteResource(final String resourceId , final String ownerId) {
        return this.getResourceById(resourceId).flatMap(resourceOptional -> {
            if (this.isNotValid(resourceOptional , ownerId)) return Mono.just(false);
            Resource resource = resourceOptional.get();
            resource.setIsActive(false);
            return this.kafkaService.sendResourceRecord(Mono.just(resource)).map(Optional::isPresent);
        });
    }
}
