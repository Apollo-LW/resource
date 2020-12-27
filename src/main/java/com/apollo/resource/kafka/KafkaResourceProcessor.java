package com.apollo.resource.kafka;

import com.apollo.resource.model.Resource;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

public class KafkaResourceProcessor {

    @Value("${resource.kafka.store}")
    String resourceStateStoreName;

    @Bean
    public Function<KStream<String , Resource>, KTable<String , Resource>> resourceProcessor() {
        return resourceRecord -> resourceRecord.groupByKey().reduce((resource , updatedResource) -> updatedResource , Materialized.as(this.resourceStateStoreName));
    }

}
