package com.apollo.resource.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Data
public class Document extends Resource {

    private final String documentId = UUID.randomUUID().toString();
    private final Date documentDateOfCreation = Calendar.getInstance().getTime();
    private String documentName = this.getResourcesOwnerId() + "-" + documentDateOfCreation;// how to get owner's name
    private String documentUrl, OwnerId = this.getResourcesOwnerId();//check this
    private boolean isPublic = false;
}
