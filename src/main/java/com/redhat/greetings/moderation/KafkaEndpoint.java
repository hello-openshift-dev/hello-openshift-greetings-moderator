package com.redhat.greetings.moderation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class KafkaEndpoint {

    static final Logger LOGGER = LoggerFactory.getLogger(KafkaEndpoint.class);

    @Inject
    GreetingModerator greetingModerator;

    @Channel("greetings-out")
    Emitter<ValidatedGreeting> greetingEmitter;

    @Incoming("greetings-in")
    public void onValidateGreetingCommand(ValidateGreetingCommand validateGreetingCommand) {

        LOGGER.debug("ValidateGreetingCommand received: {}", validateGreetingCommand);

        boolean isValid = greetingModerator.isValid(validateGreetingCommand);

        LOGGER.debug("{} is {}", validateGreetingCommand, isValid);

        greetingEmitter.send(new ValidatedGreeting(validateGreetingCommand.text(), isValid));

        LOGGER.debug("ValidatedGreeting sent");
    }
}
