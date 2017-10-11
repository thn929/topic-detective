package com.thn929.topology;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.*;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.test.ProcessorTopologyTestDriver;
import org.apache.kafka.test.StreamsTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BaseTopologyBuilderTest {

    protected static final Serializer STRING_SERIALIZER = new StringSerializer();
    protected static final Deserializer STRING_DESERIALIZER = new StringDeserializer();

    protected static final String STRING_SERDE_CLASSNAME = Serdes.String().getClass().getName();

    protected ProcessorTopologyTestDriver driver;

    protected StreamsConfig streamsConfig;

    @BeforeEach
    void setUp() {
        streamsConfig = new StreamsConfig(StreamsTestUtils.getStreamsConfig("appId","anyserver:9092", STRING_SERDE_CLASSNAME, STRING_SERDE_CLASSNAME, new Properties()));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.close();
        }
        driver = null;
    }

    private void assertNextOutputRecord(final String topic,
                                        final String key,
                                        final String value) {
        assertNextOutputRecord(topic, key, value, null, 0L);
    }

    private void assertNextOutputRecord(final String topic,
                                        final String key,
                                        final String value,
                                        final Integer partition) {
        assertNextOutputRecord(topic, key, value, partition, 0L);
    }

    private void assertNextOutputRecord(final String topic,
                                        final String key,
                                        final String value,
                                        final Integer partition,
                                        final Long timestamp) {
        ProducerRecord<String, String> record = driver.readOutput(topic, STRING_DESERIALIZER, STRING_DESERIALIZER);
        assertEquals(topic, record.topic());
        assertEquals(key, record.key());
        assertEquals(value, record.value());
        assertEquals(partition, record.partition());
        assertEquals(timestamp, record.timestamp());
    }

    private void assertNoOutputRecord(String topic) {
        assertNull(driver.readOutput(topic));
    }

}
