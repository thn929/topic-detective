package com.thn929.dao;

import com.thn929.KeyValueBean;
import com.thn929.RangeFunction;

import java.util.List;

public interface StoreDao {

    List<KeyValueBean> getAll(final String storeName) throws Exception;

    List<KeyValueBean> getRange(final String storeName, final RangeFunction<String, String> rangeFunction) throws Exception;

}
