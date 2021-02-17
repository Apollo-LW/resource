package com.apollo.resource.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@RequiredArgsConstructor
public class SharableResource {

    private String ownerId, resourceId;
    private Set<String> userIds;

}

