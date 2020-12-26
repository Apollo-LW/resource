package com.apollo.resource.model;

import lombok.Data;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

@Data
public class Resource {

    private final String resourceId = UUID.randomUUID().toString();
    private final Date resourceCreationDate = Calendar.getInstance().getTime();
    private String resourceName, resourceUrl, resourcesOwnerId;
    private boolean isActive, isPublic;
    private HashSet<String> resourceViewers;

}







