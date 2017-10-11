package com.thn929.topology;

import com.thn929.processor.KeyValueStoreProcessor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.SystemTime;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.state.Stores;
import org.apache.kafka.streams.state.internals.KeyValueStoreBuilder;

import java.util.Set;

public class BasicTopologyBuilder {

    public Topology build(final Set<String> topics, final int maxCacheSize) {
        final StreamsBuilder streamsBuilder = new StreamsBuilder();

        topics.forEach(topic -> {
            final KeyValueStoreBuilder<String, String> storeBuilder = new KeyValueStoreBuilder<>(
                    Stores.lruMap(topic, maxCacheSize),
                    Serdes.String(),
                    Serdes.String(),
                    SystemTime.SYSTEM);

            streamsBuilder.addStateStore(storeBuilder);

            final KStream<String, String> stream = streamsBuilder.stream(topic);

            stream.process(() -> new KeyValueStoreProcessor(topic), topic);

        });

        final Topology topology = streamsBuilder.build();

        return topology;
    }
}
