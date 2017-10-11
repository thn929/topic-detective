package com.thn929.topology;

import org.apache.kafka.streams.Topology;
import org.apache.kafka.test.ProcessorTopologyTestDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class BasicTopologyBuilderTest extends BaseTopologyBuilderTest {

    private static final String SOURCE_TOPIC = "source";
    private static final int MAX_CACHE_SIZE = 100;

    @Test
    void test() {
        final Topology topology = new BasicTopologyBuilder().build(Collections.singleton(SOURCE_TOPIC), MAX_CACHE_SIZE);
        driver = new ProcessorTopologyTestDriver(streamsConfig, topology);

        driver.process(SOURCE_TOPIC, "key".getBytes(), "value".getBytes());
    }

}