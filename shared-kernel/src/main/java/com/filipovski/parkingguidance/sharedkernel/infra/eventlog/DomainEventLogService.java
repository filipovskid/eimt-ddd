package com.filipovski.parkingguidance.sharedkernel.infra.eventlog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainEvent;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DomainEventLogService {
    private final StoredDomainEventRepository storedDomainEventRepository;
    private final ObjectMapper objectMapper;

    public DomainEventLogService(StoredDomainEventRepository storedDomainEventRepository,
                                 ObjectMapper objectMapper) {
        this.storedDomainEventRepository = storedDomainEventRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void append(@NonNull DomainEvent domainEvent) {
        StoredDomainEvent storedDomainEvent = new StoredDomainEvent(domainEvent, objectMapper);
        storedDomainEventRepository.saveAndFlush(storedDomainEvent);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<StoredDomainEvent> retrieveLogs(@NonNull Long lastProcessedId) {
        Long max = storedDomainEventRepository.findHighestDomainEventId();
        if(max == null)
            max = 0L;

        return storedDomainEventRepository.findEventsBetween(lastProcessedId, max);
    }
}
