package com.apollo.resource.model;

import lombok.Data;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Data
public class Video {

    private final String videoId = UUID.randomUUID().toString();
    private final Date videoDateOfCreation = Calendar.getInstance().getTime();
    private String videoName, videoUrl, ownerId, videoLogoUrl;
    private boolean isPublic;

}
