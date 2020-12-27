package com.apollo.resource.kafka;

import com.apollo.resource.model.Resource;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.internals.ConsumerFactory;
import reactor.kafka.receiver.internals.DefaultKafkaReceiver;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.internals.DefaultKafkaSender;
import reactor.kafka.sender.internals.ProducerFactory;

import java.util.Collections;
import java.util.Properties;

public class KafkaConfiguration {

    @Value("${resource.kafka.server}")
    private String bootstrapServer;
    @Value("${resource.kafka.topic}")
    private String topicName;
    @Value("${resource.kafka.partition}")
    private Integer numberOfPartitions;
    @Value("${resource.kafka.replicas}")
    private Short numberOfReplicas;
    @Value("${resource.kafka.retention}")
    private String retentionPeriod;
    @Value("${resource.kafka.acks}")
    private String numberOfAcks;
    @Value("${resource.kafka.retries}")
    private Integer numberOfRetries;
    @Value("${resource.kafka.requestTimeOut}")
    private String requestTimeout;
    @Value("${resource.kafka.batch}")
    private String batchSize;
    @Value("${resource.kafka.linger}")
    private String linger;
    @Value("${resource.kafka.max-in-flight}")
    private String maxInFlight;
    @Value("${resource.kafka.client-id}")
    private String clientId;
    @Value("${resource.kafka.group-id}")
    private String groupId;
    @Value("${resource.kafka.offset}")
    private String offset;

    @Bean
    public NewTopic createResourceTopic() {
        return TopicBuilder
                .name(this.topicName)
                .partitions(this.numberOfPartitions)
                .replicas(this.numberOfReplicas)
                .config(TopicConfig.RETENTION_MS_CONFIG , this.retentionPeriod)
                .build();
    }


    @Bean
    KafkaSender resourceKafkaSender() {
        final Properties resourceSenderProperties = new Properties();
        resourceSenderProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG , this.bootstrapServer);
        resourceSenderProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG , StringSerializer.class);
        resourceSenderProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG , JsonSerializer.class);
        resourceSenderProperties.put(ProducerConfig.ACKS_CONFIG , this.numberOfAcks);
        resourceSenderProperties.put(ProducerConfig.RETRIES_CONFIG , this.numberOfRetries);
        resourceSenderProperties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG , this.requestTimeout);
        resourceSenderProperties.put(ProducerConfig.BATCH_SIZE_CONFIG , this.batchSize);
        resourceSenderProperties.put(ProducerConfig.LINGER_MS_CONFIG , this.linger);
        resourceSenderProperties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION , this.maxInFlight);

        return new DefaultKafkaSender<String, Resource>(ProducerFactory.INSTANCE , SenderOptions.create(resourceSenderProperties));
    }

    @Bean
    KafkaReceiver resourceKafkaReceiver() {
        final Properties resourceReceiverProperties = new Properties();
        resourceReceiverProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG , this.bootstrapServer);
        resourceReceiverProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG , StringDeserializer.class);
        resourceReceiverProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG , JsonDeserializer.class);
        resourceReceiverProperties.put(ConsumerConfig.CLIENT_ID_CONFIG , this.clientId);
        resourceReceiverProperties.put(ConsumerConfig.GROUP_ID_CONFIG , this.groupId);
        resourceReceiverProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG , true);
        resourceReceiverProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG , this.offset);

        return new DefaultKafkaReceiver(ConsumerFactory.INSTANCE , ReceiverOptions.create(resourceReceiverProperties).subscription(Collections.singleton(this.topicName)));
    }
}
