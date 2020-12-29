package com.apollo.resource.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.UUID;

@RequiredArgsConstructor
@Data
public class ResourceUser {

    private HashSet<Resource> userResources = new HashSet<>();

    public ResourceUser addResource (Resource resource){
        this.userResources.add(resource);
        return this;
    }

}
