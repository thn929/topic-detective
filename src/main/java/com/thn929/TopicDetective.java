package com.thn929;

import org.apache.kafka.streams.KafkaStreams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringBootApplication
public class TopicDetective {

    private KafkaStreams streams;

    @Autowired
    public TopicDetective(KafkaStreams streams) {
        this.streams = streams;
    }

    public static void main(String[] args) {
        SpringApplication.run(TopicDetective.class, args);
    }

    @PostConstruct
    public void postConstruct() {
        streams.start();
    }

    @PreDestroy
    public void preDestroy() {
        if (streams != null) {
            streams.close();
        }
    }
}
