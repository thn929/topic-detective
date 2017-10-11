package com.thn929.controller;

import com.thn929.TopicDetectiveConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class TopicDetectiveController {

    private final TopicDetectiveConfig config;

    @Autowired
    public TopicDetectiveController(TopicDetectiveConfig config) {
        this.config = config;
    }

    @RequestMapping("/topics")
    public Set<String> topics() {return config.getTopics();}
}
