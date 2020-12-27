package com.apollo.resource.service;

import com.apollo.resource.model.Resource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ResourceService {

    Mono<Resource> postResource(Mono<Resource> resourceMono);

    Mono<Boolean> updateResource(Mono<Resource> resourceMono);

    Mono<Boolean> shareResourceFlag(String ownerId, String resourceId, boolean flag);

    Mono<Boolean> deleteResource (String resourceId);
}
