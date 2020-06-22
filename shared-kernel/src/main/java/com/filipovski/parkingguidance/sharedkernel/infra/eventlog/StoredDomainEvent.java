package com.filipovski.parkingguidance.sharedkernel.infra.eventlog;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainEvent;
import lombok.NonNull;

import javax.naming.LimitExceededException;
import javax.naming.SizeLimitExceededException;
import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
public class StoredDomainEvent {

    private static final int DOMAIN_EVENT_JSON_MAX_LENGTH = 1024;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "occurred_on", nullable = false)
    private Instant occurredOn;

    @Column(name = "domain_event_class_name", nullable = false)
    private String domainEventClassName;

    @JsonRawValue
    @Column(name = "domain_event_body", nullable = false, length = DOMAIN_EVENT_JSON_MAX_LENGTH)
    private String domainEventBody;

    @Transient
    private Class<? extends DomainEvent> domainEventClass;

    public StoredDomainEvent() { }

    public StoredDomainEvent(@NonNull DomainEvent domainEvent, @NonNull ObjectMapper objectMapper) {
        Objects.requireNonNull(domainEvent, "domainEvent should not be null");
        Objects.requireNonNull(objectMapper, "objectMapper should not be null");

        domainEventClass = domainEvent.getClass();
        domainEventClassName = domainEventClass.getName();
        occurredOn = domainEvent.occuredOn();

        try {
            domainEventBody = objectMapper.writeValueAsString(domainEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("Could not serialize domain event [%s] to JSON",
                    domainEvent.getClass().getCanonicalName()), e);
        }

        if (domainEventBody.length() > DOMAIN_EVENT_JSON_MAX_LENGTH) {
            throw new RuntimeException("Domain event JSON string is too long");
        }
    }

    @NonNull
    public DomainEvent toDomainEvent(@NonNull ObjectMapper objectMapper) {
        return toDomainEvent(objectMapper, domainEventClass());
    }

    @NonNull
    public <T extends DomainEvent> T toDomainEvent(@NonNull ObjectMapper objectMapper,
                                                   @NonNull Class<T> domainEventClass) {
        Objects.requireNonNull(objectMapper, "objectMapper must not be null");
        Objects.requireNonNull(domainEventClass, "domainEventClass must not be null");

        try {
            return objectMapper.readValue(domainEventBody, domainEventClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not deserialize JSON to domain event", e);
        }
    }

    @NonNull
    public Long id() {
        if (id == null) {
            throw new IllegalStateException("The StoredDomainEvent has not been saved yet");
        }
        return id;
    }

    @NonNull
    public Instant occurredOn() {
        return occurredOn;
    }

    @NonNull
    public String domainEventClassName() {
        return domainEventClassName;
    }

    @SuppressWarnings("unchecked")
    private Class<? extends DomainEvent> lookupDomainEventClass() {
        try {
            return (Class<? extends DomainEvent>) Class.forName(domainEventClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Could not load domain event class", e);
        }
    }

    @NonNull
    public Class<? extends DomainEvent> domainEventClass() {
        if (domainEventClass == null) {
            domainEventClass = lookupDomainEventClass();
        }
        return domainEventClass;
    }
}
