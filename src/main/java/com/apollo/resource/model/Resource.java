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
    private String resourceName = this.resourceId + "-" + this.resourceCreationDate;
    private ResourceType resourceType;
    private String resourcesOwnerId, resourceUrl;
    private Boolean isActive = true, isPublic = false;
    private HashSet<String> resourceViewers = new HashSet<>();

    public void addResourceViewer(String userId) {
        this.resourceViewers.add(userId);
    }

    public void removeResourceViewer(String userId) {
        this.resourceViewers.remove(userId);
    }


}







