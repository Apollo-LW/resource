package com.apollo.resource.service;

import com.apollo.resource.model.Resource;
import reactor.core.publisher.Flux;

public interface ResourceUserService {

    Flux<Resource> getUserResources(String userId);
}
