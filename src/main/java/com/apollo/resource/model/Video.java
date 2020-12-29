package com.apollo.resource.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Data
public class Video extends Resource {

    private String videoName = this.getResourcesOwnerId() + "-" + getResourceCreationDate();
    private String videoUrl, ownerId, videoLogoUrl;
    private boolean isPublic;

}
