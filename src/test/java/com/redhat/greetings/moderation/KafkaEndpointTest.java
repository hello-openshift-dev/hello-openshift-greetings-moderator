package com.redhat.greetings.moderation;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.smallrye.reactive.messaging.memory.InMemoryConnector;
import io.smallrye.reactive.messaging.memory.InMemorySink;
import io.smallrye.reactive.messaging.memory.InMemorySource;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@QuarkusTest
public class KafkaEndpointTest {

    static final Logger LOGGER = LoggerFactory.getLogger(KafkaEndpointTest.class);

    @Inject
    KafkaEndpoint kafkaEndpoint;

    @InjectSpy
    GreetingModerator greetingModerator;

    @Inject @Any
    InMemoryConnector connector;

    InMemorySource<ValidateGreetingCommand> greetingsSource;

    InMemorySink<ValidatedGreeting> greetingsSink;

    @Test
    public void testIncomingGreeting() {

        greetingsSource.send(new ValidateGreetingCommand("Hi, there!"));
        ArgumentCaptor<ValidateGreetingCommand> argumentCaptor = ArgumentCaptor.forClass(ValidateGreetingCommand.class);
        Mockito.verify(greetingModerator, Mockito.times(1)).isValid(argumentCaptor.capture());
    }

    @BeforeEach
    public void setUp() {

        greetingsSource = connector.source("greetings-in");
        // 5. Retrieves the in-memory sink to check what is received
        greetingsSink = connector.sink("greetings-out");
    }

    @BeforeAll
    public static void switchMyChannels() {
        InMemoryConnector.switchIncomingChannelsToInMemory("greetings-in");
        InMemoryConnector.switchOutgoingChannelsToInMemory("greetings-out");
    }

}
