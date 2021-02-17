package com.apollo.resource.kafka.processor;

import com.apollo.resource.model.Resource;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ResourceProcessor {

    @Value("${resource.kafka.store}")
    String resourceStateStoreName;

    @Bean
    public Function<KStream<String, Resource>, KTable<String, Resource>> resourceProcessorState() {
        return resourceKStream -> resourceKStream
                .groupByKey()
                .reduce((resource , updatedResource) -> updatedResource , Materialized.as(this.resourceStateStoreName));
    }

}
