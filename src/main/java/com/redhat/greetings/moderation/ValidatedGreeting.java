package com.redhat.greetings.moderation;

public record ValidatedGreeting(String text, boolean isValid) {
}
