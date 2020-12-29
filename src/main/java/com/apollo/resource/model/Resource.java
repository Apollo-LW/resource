package com.apollo.resource.model;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Data
@RequiredArgsConstructor
public class Resource {

    private final String resourceId = UUID.randomUUID().toString();
    private final Date resourceCreationDate = Calendar.getInstance().getTime();
    private String resourceName = this.resourceId + "-" + this.resourceCreationDate;

    AmazonS3Client s3Client = (AmazonS3Client) AmazonS3ClientBuilder.defaultClient();
    private String resourceUrl = s3Client.getUrl(resourceName,resourceId).toString();

    private String resourcesOwnerId;
    private boolean isActive =true, isPublic = false;
    private HashSet<String> resourceViewers = new HashSet<>();

    public void addResourceViewer (String userId){
        this.resourceViewers.add(userId);
    }
    public void removeResourceViewer (String userId){this.resourceViewers.remove(userId);}


}







