package com.apollo.resource.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.UUID;

@RequiredArgsConstructor
@Data
public class ResourceOwner {

    private final String ownerId = UUID.randomUUID().toString();
    private HashSet<Resource> userResources = new HashSet<>();

    public ResourceOwner addResource (Resource resource){
        this.userResources.add(resource);
        return this;
    }

}
