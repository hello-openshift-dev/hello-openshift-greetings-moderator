package com.redhat.greetings.moderation;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class GreetingsModeratorTest {

    static final Logger LOGGER = LoggerFactory.getLogger(GreetingsModeratorTest.class);

    @Inject
    GreetingModerator greetingModerator;

    @Test
    public void testValidContent() {

        ValidateGreetingCommand validateGreetingCommand = new ValidateGreetingCommand("Hi, there!");
        assertTrue(greetingModerator.isValid(validateGreetingCommand));
    }

    @Test
    public void testInValidContent() {

        ValidateGreetingCommand validateGreetingCommand = new ValidateGreetingCommand("Hi, VMWare!");
        assertFalse(greetingModerator.isValid(validateGreetingCommand));
    }
}
