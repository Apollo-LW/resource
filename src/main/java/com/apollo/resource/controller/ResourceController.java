package com.apollo.resource.controller;


import com.apollo.resource.model.Resource;
import com.apollo.resource.service.ResourceService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController

public class ResourceController {

    private ResourceService resourceService;

   // @PutMapping(value= "/resourceId")
    //public Mono<Boolean> updateResource(@RequestBody Mono<Resource> resource){return resourceService.updateResource();}





}
