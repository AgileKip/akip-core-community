package org.akip.web.rest;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HeaderConstants {

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public static String APPLICATION_NAME;

    @PostConstruct
    public void init() {
        APPLICATION_NAME = applicationName;
    }
}