package com.apollo.resource.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SharableResource {

    private String userId, resourceId;

}

