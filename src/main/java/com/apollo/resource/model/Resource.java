package com.apollo.resource.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Data
@RequiredArgsConstructor
public class Resource {

    private final String resourceId = UUID.randomUUID().toString();
    private final Date resourceCreationDate = Calendar.getInstance().getTime();
    private String resourceName = this.resourceId + "-" + this.resourceCreationDate;
    private String resourceUrl;
    private String resourcesOwnerId;
    private boolean isActive =true, isPublic = false;
    private HashSet<String> resourceViewers = new HashSet<>();

    public void addResourceViewer (List<String> ViewersIds){
        this.resourceViewers.addAll(ViewersIds);
    }


}







