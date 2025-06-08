package com.jrpolesi.model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class ToDo {
    private final UUID id;
    private final String title;
    private final String description;
    private final ZonedDateTime createdAt;
    private boolean completed;

    private ToDo(UUID id, String title, String description, boolean completed, ZonedDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;

        validate();
    }

    private ToDo(String title, String description, boolean completed) {
        this(UUID.randomUUID(), title, description, completed, ZonedDateTime.now());
    }

    public static ToDo with(String title, String description, boolean completed) {
        return new ToDo(title, description, completed);
    }

    public static ToDo of(UUID id, String title, String description, boolean completed, ZonedDateTime createdAt) {
        return new ToDo(id, title, description, completed, createdAt);
    }

    public void markAsCompleted() {
        this.completed = true;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    private void validate() {
        final var errors = new ArrayList<Exception>();

        if (id == null) {
            errors.add(new IllegalArgumentException("'id' cannot be null"));
        }
        if (title == null || title.isBlank()) {
            errors.add(new IllegalArgumentException("'title' cannot be null or blank"));
        }
        if (description == null || description.isBlank()) {
            errors.add(new IllegalArgumentException("'description' cannot be null or blank"));
        }
        if (createdAt == null) {
            errors.add(new IllegalArgumentException("'createdAt' cannot be null"));
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException("Invalid To Do: " + errors);
        }
    }
}
