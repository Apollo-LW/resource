package com.apollo.resource.controller;

import com.apollo.resource.model.Resource;
import com.apollo.resource.service.ResourceUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;


public class ResourceUserController {

    private ResourceUserService resourceUserService;

    @GetMapping("/resource/user/{userId}")
    public Flux<Resource> getUserResource (@PathVariable("userId") String userId){
        return this.resourceUserService.getUserResources(userId);
    }
}
