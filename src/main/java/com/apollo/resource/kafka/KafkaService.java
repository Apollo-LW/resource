package com.apollo.resource.kafka;

import com.apollo.resource.model.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@CommonsLog(topic = "Kafka Service")
public class KafkaService {

    @Value("${resource.kafka.topic}")
    private String resourceTopicName;
    private final KafkaSender<String, Resource> resourceKafkaSender;

    

    public Mono<Optional<Resource>> sendResourceRecord(Mono<Resource> resourceMono){
        return resourceMono.flatMap(resource -> this.resourceKafkaSender
                .send(Mono.just(SenderRecord.create (new ProducerRecord<String, Resource>(this.resourceTopicName, resource.getResourceId(), resource),1)))
                .next().doOnNext(log :: info).doOnError(log :: error)
                .map(integerSenderResult -> integerSenderResult.exception() == null ? Optional.of(resource): Optional.empty()));
    }
}
