package com.filipovski.parkingguidance.sharedkernel.infra.eventlog;

import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainEvent;

import java.util.Optional;

public interface RemoteEventTranslator {
    boolean supports(StoredDomainEvent storedDomainEvent);

    Optional<DomainEvent> translate(StoredDomainEvent storedDomainEvent);
}
