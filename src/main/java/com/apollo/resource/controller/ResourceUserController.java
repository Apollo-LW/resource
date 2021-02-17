package com.apollo.resource.controller;

import com.apollo.resource.model.Resource;
import com.apollo.resource.service.ResourceUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resource/user")
public class ResourceUserController {

    private final ResourceUserService resourceUserService;

    @GetMapping("/{userId}")
    public Flux<Resource> getUserResource (@PathVariable("userId") String userId){
        return this.resourceUserService.getUserResources(userId);
    }
}
