package model;

import lombok.Data;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Data
public class Document {

    private final String documentId = UUID.randomUUID().toString();
    private final Date documentDateOfCreation = Calendar.getInstance().getTime();
    private String documentName, documentUrl, OwnerId;
    private boolean isPublic;
}
