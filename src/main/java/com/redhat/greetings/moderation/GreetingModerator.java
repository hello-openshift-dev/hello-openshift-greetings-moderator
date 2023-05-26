package com.redhat.greetings.moderation;

import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class GreetingModerator {

    static final Logger LOGGER = LoggerFactory.getLogger(GreetingModerator.class);

    public boolean isValid(Greeting greeting) {

        return greeting.text().contains("VMWare") ? false : true;
    }
}
