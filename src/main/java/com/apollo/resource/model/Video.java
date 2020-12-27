package com.apollo.resource.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Data
public class Video extends Resource {

    private final String videoId = UUID.randomUUID().toString();
    private final Date videoDateOfCreation = Calendar.getInstance().getTime();
    private String videoName, videoUrl, ownerId, videoLogoUrl;
    private boolean isPublic;

}
