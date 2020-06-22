package com.filipovski.parkingguidance.sharedkernel.domain.base;

import com.filipovski.parkingguidance.sharedkernel.infra.eventlog.StoredDomainEvent;

import java.util.List;

public interface RemoteEventLog {
    List<StoredDomainEvent> events();
}
