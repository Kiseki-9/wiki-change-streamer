package com.arthur.springboot.handler;

import com.arthur.springboot.model.ApiResponse;
import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

public class WikimediaChangesHandler implements EventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesHandler.class.getName());
    private KafkaTemplate<String,ApiResponse> kafkaTemplate;
    private String topic;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public WikimediaChangesHandler(KafkaTemplate<String, ApiResponse> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void onOpen() throws Exception {

    }

    @Override
    public void onClosed() throws Exception {

    }

    @Override
    public void onMessage(String s, MessageEvent messageEvent) throws Exception {
        String data = messageEvent.getData();
        try{
            ApiResponse apiResponse = objectMapper.readValue(data, ApiResponse.class);
            kafkaTemplate.send(topic,apiResponse);
            LOGGER.info(String.format("Api Response -> %s", apiResponse.toString()));
        }
        catch (JacksonException e){
            LOGGER.error("Error parsing JSON data");
        }
    }

    @Override
    public void onComment(String s) throws Exception {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}
