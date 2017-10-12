package com.thn929.dao;

import com.thn929.KeyValueBean;
import com.thn929.RangeFunction;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;

import java.util.ArrayList;
import java.util.List;

public class ReadOnlyKeyValueStoreDao implements StoreDao {

    private KafkaStreams streams;

    public ReadOnlyKeyValueStoreDao(KafkaStreams streams) {
        this.streams = streams;
    }

    private final RangeFunction<String, String> ALL = ReadOnlyKeyValueStore::all;

    @Override
    public List<KeyValueBean> getAll(String storeName) throws Exception {
        return getRange(storeName, ALL);
    }

    @Override
    public List<KeyValueBean> getRange(String storeName, RangeFunction<String, String> rangeFunction) throws Exception {
        final ReadOnlyKeyValueStore<String, String> store = streams.store(storeName, QueryableStoreTypes.keyValueStore());
        if (store == null) {
            throw new Exception(storeName + " not found");
        }

        final List<KeyValueBean> results = new ArrayList<>();
        final KeyValueIterator<String, String> iterator = rangeFunction.apply(store);

        while (iterator.hasNext()) {
            final KeyValue<String, String> next = iterator.next();
            results.add(new KeyValueBean(next.key, next.value));
        }
        iterator.close();

        return results;

    }
}
