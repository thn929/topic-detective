package com.thn929.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class KeyValueStoreProcessor implements Processor<String, String> {

    private String topic;

    private KeyValueStore<String, String> store;

    private ProcessorContext context;

    private AtomicLong keyCounter = new AtomicLong();

    public KeyValueStoreProcessor(String topic) {
        this.topic = topic;
    }

    @Override
    public void init(ProcessorContext context) {
        this.store = (KeyValueStore<String, String>) context.getStateStore(topic);
        this.context = context;
    }

    @Override
    public void process(String key, String value) {
        if (key != null) {
            final String keyAndCount = key + "." + keyCounter.incrementAndGet();
            store.put(keyAndCount, value);
            log.info("Put [{}, {}] to state store [{}]", keyAndCount, value, topic);
        } else {
            log.info("Skipping... key was null!");
        }
    }

    @Override
    public void punctuate(long l) {

    }

    @Override
    public void close() {

    }
}
