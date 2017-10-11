package com.thn929;

import com.thn929.topology.BasicTopologyBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Configuration
public class TopicDetectiveConfig {

    @Value("${bootstrap.servers:localhost:9092}")
    private String bootstrapServers;

    @Value("${application.id:}")
    private String applicationId;

    @Value("${max.cache.size:10000}")
    private int maxCacheSize;

    @Getter
    @Value("#{'${topics}'.split(',')}")
    private Set<String> topics;

    @Bean
    public KafkaStreams streams() {
        return new KafkaStreams(new BasicTopologyBuilder().build(topics, maxCacheSize), streamsConfig());
    }


    @Bean
    public StreamsConfig streamsConfig() {
        final String uuid = UUID.randomUUID().toString();

        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, StringUtils.isNotBlank(applicationId) ? applicationId : TopicDetective.class.getSimpleName() + "-" + uuid);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new StreamsConfig(properties);
    }
}
