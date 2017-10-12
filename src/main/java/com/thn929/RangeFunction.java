package com.thn929;

import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;

import java.util.function.Function;

public interface RangeFunction<K, V> extends Function<ReadOnlyKeyValueStore<K, V>, KeyValueIterator<K, V>> {
}
