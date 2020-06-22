package com.filipovski.parkingguidance.sharedkernel.infra.eventlog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoredDomainEventRepository extends JpaRepository<StoredDomainEvent,Long> {
}
