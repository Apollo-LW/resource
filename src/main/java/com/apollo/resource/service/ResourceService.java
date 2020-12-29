package com.apollo.resource.service;

import com.apollo.resource.model.Resource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface ResourceService {

    Mono<Optional<Resource>> getResourceByID (String resourceId);

    Mono<Resource> postResource(Mono<Resource> resourceMono);

    Mono<Boolean> updateResource(Mono<Resource> resourceMono);

    Mono<Boolean> shareResource(String userId, String resourceId, boolean flag);

    Mono<Boolean> deleteResource (String resourceId);
}
