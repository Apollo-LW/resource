package com.apollo.resource.kafka;

import com.apollo.resource.model.Resource;
import com.apollo.resource.model.ResourceUser;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.jetbrains.annotations.Contract;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class CustomSerdes {

    static public final class ResourceSerde extends Serdes.WrapperSerde<Resource> {
        public ResourceSerde() {
            super(new JsonSerializer<>() , new JsonDeserializer<>(Resource.class));
        }
    }

    static public final class ResourceUserSerde extends Serdes.WrapperSerde<ResourceUser> {
        public ResourceUserSerde() {
            super(new JsonSerializer<>() , new JsonDeserializer<>(ResourceUser.class));
        }
    }

    @Contract(" -> new ")
    public static Serde<Resource> resourceSerde() {
        return new CustomSerdes.ResourceSerde();
    }

    @Contract(" -> new ")
    public static Serde<ResourceUser> resourceUserSerde() {
        return new CustomSerdes.ResourceUserSerde();
    }


}
