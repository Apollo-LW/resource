package model;

import lombok.RequiredArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

@RequiredArgsConstructor
@Data
public class Resource {

    private final String resourceId = UUID.randomUUID().toString();
    private final Date resourceCreationDate = Calendar.getInstance().getTime();
    private String resourceName, resourceUrl, resourcesOwnerId;
    private boolean isActive, isPublic;
    private HashSet<String> resourceViewers;

}







