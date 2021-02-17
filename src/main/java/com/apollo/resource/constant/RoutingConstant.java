package com.apollo.resource.constant;

public abstract class RoutingConstant {

    public static final String RESOURCE_ID = "resourceId";
    public static final String RESOURCE_ID_PATH = "/{" + RESOURCE_ID + "}";
    public static final String OWNER_ID = "ownerId";
    public static final String OWNER_ID_PATH = "/{" + OWNER_ID + "}";
    public static final String FLAG = "flag";
    public static final String FLAG_PATH = "/{" + FLAG + "}";
    public static final String RESOURCE_PATH = "/resource";
    public static final String SHARE_PATH = "/share";
    public static final String SHARE_FLAG_PATH = SHARE_PATH + FLAG_PATH;
    public static final String USER_PATH = "/user";
    public static final String USER_ID = "userId";
    public static final String USER_ID_PATH = "/{" + USER_ID + "}";
    public static final String RESOURCE_USER_PATH = RESOURCE_PATH + USER_PATH;
    public static final String RESOURCE_OWNER_ID = RESOURCE_ID_PATH + OWNER_ID_PATH;
}
