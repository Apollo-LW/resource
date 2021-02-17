package com.apollo.resource.kafka.processor;

import com.apollo.resource.kafka.CustomSerdes;
import com.apollo.resource.model.Resource;
import com.apollo.resource.model.ResourceUser;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ResourceUserProcessor {

    @Value("${user.kafka.store}")
    private String resourceUserStateStoreName;

    @Bean
    public Function<KStream<String, Resource>, KTable<String, ResourceUser>> resourceUserProcessorState() {
        return resourceKStream -> resourceKStream
                .flatMap((resourceId , resource) -> resource
                        .getResourceViewers()
                        .stream()
                        .map(resourceViewer -> new KeyValue<String, Resource>(resourceViewer , resource))
                        .collect(Collectors.toSet()))
                .groupByKey(Grouped.with(Serdes.String() , CustomSerdes.resourceSerde()))
                .aggregate(ResourceUser::new ,
                        ( (resourceViewer , resource , resourceUser) -> resourceUser.addResource(resource) ) ,
                        Materialized.with(Serdes.String() , CustomSerdes.resourceUserSerde()))
                .toStream()
                .groupByKey(Grouped.with(Serdes.String() , CustomSerdes.resourceUserSerde()))
                .reduce((resourceUser , updatedResourceUser) -> updatedResourceUser , Materialized.as(this.resourceUserStateStoreName));
    }

}
