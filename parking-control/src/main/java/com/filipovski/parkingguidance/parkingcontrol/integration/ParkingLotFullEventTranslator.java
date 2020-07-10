package com.filipovski.parkingguidance.parkingcontrol.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainEvent;
import com.filipovski.parkingguidance.sharedkernel.infra.eventlog.RemoteEventTranslator;
import com.filipovski.parkingguidance.sharedkernel.infra.eventlog.StoredDomainEvent;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingLotFullEventTranslator implements RemoteEventTranslator {
    private final ObjectMapper objectMapper;

    public ParkingLotFullEventTranslator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(StoredDomainEvent storedDomainEvent) {
        return storedDomainEvent.domainEventClassName()
                .equals("com.filipovski.parkingguidance.parkingmanager.domain.event.ParkingLotFull");
    }

    @Override
    public Optional<DomainEvent> translate(StoredDomainEvent storedDomainEvent) {
        return Optional.of(storedDomainEvent.toDomainEvent(objectMapper, ParkingLotFullEvent.class));
    }
}
