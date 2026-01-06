package com.endUser.endUser.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class LocationConsumer {

    private static final Logger logger = LoggerFactory.getLogger(LocationConsumer.class);

    @KafkaListener(topics = AppConstants.LOCATION_TOPIC_NAME, groupId = AppConstants.LOCATION_GROUP_ID)
    public void consumeLocation(String location) {
        logger.info("Received location: {}", location);
    }
}

