package com.apollo.resource.kafka;

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
public class KafkaResourceProcessor {

    @Value("${resource.kafka.store}")
    String resourceStateStoreName;
    @Value("${user.kafka.store}")
    private String resourceUserStateStoreName;

    @Bean
    public Function<KStream<String, Resource>, KTable<String, Resource>> resourceProcessor() {
        return resourceKStream -> {
            resourceKStream.flatMap((resourceId , resource) -> resource.getResourceViewers().stream().map(viewerId -> new KeyValue<String, Resource>(viewerId , resource))
                    .collect(Collectors.toSet()))
                    .groupByKey(Grouped.with(Serdes.String() , CustomSerdes.resourceSerde()))
                    .aggregate(ResourceUser::new , (viewerId , resource , resourceUser) -> resourceUser.addResource(resource) , Materialized.with(Serdes.String() ,
                            CustomSerdes.resourceUserSerde()))
                    .toStream()
                    .groupByKey(Grouped.with(Serdes.String() , CustomSerdes.resourceUserSerde()))
                    .reduce((resourceUser , updateResourceUser) -> updateResourceUser , Materialized.as(this.resourceUserStateStoreName));


            return resourceKStream.groupByKey().reduce((resource , updatedResource) -> updatedResource , Materialized.as(this.resourceStateStoreName));
        };
    }
}



