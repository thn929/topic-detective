package com.thn929.controller;

import com.thn929.TopicDetectiveConfig;
import com.thn929.dao.StoreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class TopicDetectiveController {

    private final TopicDetectiveConfig config;
    private final StoreDao storeDao;

    @Autowired
    public TopicDetectiveController(TopicDetectiveConfig config, StoreDao storeDao) {
        this.config = config;
        this.storeDao = storeDao;
    }

    @RequestMapping("/topics")
    public Set<String> topics() {return config.getTopics();}

    @RequestMapping(method = RequestMethod.GET, value="/topics/{storeName}")
    public List<?> getAll(@PathVariable final String storeName) throws Exception {
        return storeDao.getAll(storeName);
    }
}
