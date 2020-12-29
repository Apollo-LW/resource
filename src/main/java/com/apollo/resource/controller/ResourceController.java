package com.apollo.resource.controller;


import com.apollo.resource.model.Resource;
import com.apollo.resource.service.ResourceService;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController

public class ResourceController {

    private ResourceService resourceService;

    //there is no get resource by Id?

    @GetMapping(value ="/{resourceId}")
    public Mono<Resource> getResource (@PathVariable String resourceId){
        return this.resourceService.getResourceByID(resourceId).map(Optional::get);
    }

    @PostMapping(value = "/")
    public Mono<Resource> createResource(@RequestBody Resource resource){
        return this.resourceService.postResource(Mono.just(resource));//try
    }

    @PutMapping(value= "/{resourceId}")
    public Mono<Boolean> updateResource(@RequestBody Resource resource){
        return this.resourceService.updateResource(Mono.just(resource));
    }

    @PutMapping(value = "/share/{flag}")
    public Mono<Boolean> shareResource (@PathVariable ("flag") Boolean flag ,@RequestBody String userId, String resourceId){
        return this.resourceService.shareResource(userId,resourceId,flag);
    }






}
