package com.apollo.resource.controller;


import com.apollo.resource.model.Resource;
import com.apollo.resource.model.SharableResource;
import com.apollo.resource.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/resource")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @GetMapping(value ="/{resourceId}")
    public Mono<Resource> getResource (@PathVariable String resourceId){
        return this.resourceService.getResourceByID(resourceId).map(Optional::get);
    }

    @PostMapping(value = "/")
    public Mono<Resource> createResource(@RequestBody Resource resource){
        return this.resourceService.createResource(Mono.just(resource));//try
    }

    @PutMapping(value= "/")
    public Mono<Boolean> updateResource(@RequestBody Resource resource){
        return this.resourceService.updateResource(Mono.just(resource));
    }

    @PutMapping(value = "/share/{flag}")
    public Mono<Boolean> shareResource (@PathVariable ("flag") Boolean flag , @RequestBody Mono<SharableResource> sharableResourceMono){
        return this.resourceService.shareResource(sharableResourceMono,flag);
    }

    @DeleteMapping("/")
    public Mono<Boolean> deleteResource(@RequestBody String resourceId) {
        return this.resourceService.deleteResource(resourceId);
    }




}
