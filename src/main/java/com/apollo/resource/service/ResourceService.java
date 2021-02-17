package com.apollo.resource.service;

import com.apollo.resource.model.Resource;
import com.apollo.resource.model.SharableResource;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface ResourceService {

    Mono<Optional<Resource>> getResourceById(final String resourceId);

    Mono<Optional<Resource>> saveResource(final Mono<Resource> resourceMono);

    Mono<Boolean> updateResource(final Mono<Resource> resourceMono , final String ownerId);

    Mono<Boolean> shareResource(final Mono<SharableResource> sharableResourceMono , final Boolean isAdd);

    Mono<Boolean> deleteResource(final String resourceId , final String ownerId);
}
