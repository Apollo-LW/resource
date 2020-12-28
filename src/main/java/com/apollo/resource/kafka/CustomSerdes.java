package com.apollo.resource.kafka;

import org.jetbrains.annotations.Contract;
import com.apollo.resource.model.Document;
import com.apollo.resource.model.Resource;
import com.apollo.resource.model.ResourceUser;
import com.apollo.resource.model.Video;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
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

    static public final class VideoSerde extends Serdes.WrapperSerde<Video> {
        public VideoSerde() {
            super(new JsonSerializer<>() , new JsonDeserializer<>(Video.class));
        }
    }

    static public final class DocumentSerde extends Serdes.WrapperSerde<Document> {
        public DocumentSerde() {
            super(new JsonSerializer<>() , new JsonDeserializer<>(Document.class));
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

    @Contract(" -> new ")
    public static Serde<Video> videoSerde(){
        return new CustomSerdes.VideoSerde();
    }

    @Contract(" -> new ")
    public static Serde<Document> documentSerde(){
        return new CustomSerdes.DocumentSerde();
    }

}
