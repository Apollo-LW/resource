package com.apollo.resource.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Data
public class Document extends Resource {

    private String documentName = this.getResourcesOwnerId() + "-" + getResourceCreationDate();
    private String documentUrl, OwnerId = this.getResourcesOwnerId();
    private boolean isPublic = false;
}
