package com.filipovski.parkingguidance.sharedkernel.infra.port.rest;

import com.filipovski.parkingguidance.sharedkernel.infra.eventlog.DomainEventLogService;
import com.filipovski.parkingguidance.sharedkernel.infra.eventlog.StoredDomainEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/event-log")
public class EventLogController {
    private final DomainEventLogService domainEventLogService;

    public EventLogController(DomainEventLogService domainEventLogService) {
        this.domainEventLogService = domainEventLogService;
    }

    @GetMapping(path="/{lastProcessedId}")
    public ResponseEntity<List<StoredDomainEvent>> domainEvents(@PathVariable Long lastProcessedId) {
        return ResponseEntity.ok().body(domainEventLogService.retrieveLogs(lastProcessedId));
    }
}
