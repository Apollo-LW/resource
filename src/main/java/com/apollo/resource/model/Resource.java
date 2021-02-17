package com.apollo.resource.model;

import lombok.Data;

import java.util.*;

@Data
public class Resource {

    private final String resourceId = UUID.randomUUID().toString();
    private final Date resourceCreationDate = Calendar.getInstance().getTime();
    private String resourceName = this.resourceId + "-" + this.resourceCreationDate;
    private ResourceType resourceType;
    private String resourceUrl;
    private Boolean isActive = true, isPublic = false;
    private Set<String> resourcesOwners = new HashSet<>(), resourceViewers = new HashSet<>();

    public Boolean addResourceViewers(Set<String> userIds) {
        return this.resourceViewers.addAll(userIds);
    }

    public Boolean removeResourceViewers(Set<String> userIds) {
        return this.resourceViewers.removeAll(userIds);
    }

    public Boolean doesNotHaveOwner(String ownerId) {
        return !this.resourcesOwners.contains(ownerId);
    }

}







