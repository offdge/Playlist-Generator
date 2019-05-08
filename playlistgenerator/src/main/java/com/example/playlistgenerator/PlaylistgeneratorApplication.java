package com.example.playlistgenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PlaylistgeneratorApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaylistgeneratorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PlaylistgeneratorApplication.class, args);
        LOGGER.info("log this");
        LOGGER.info("log this");
        LOGGER.info("log this");
        LOGGER.info("log this");
        LOGGER.info("log this");
        LOGGER.info("log that");
        LOGGER.info("log that");
        LOGGER.info("log that");
        LOGGER.info("log that");
        LOGGER.info("log that {}", "sUPER RAZOCHAROVAN");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
