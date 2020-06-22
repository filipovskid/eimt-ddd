package com.filipovski.parkingguidance.sharedkernel.infra.eventlog;

import com.filipovski.parkingguidance.sharedkernel.domain.base.DomainObjectId;
import com.filipovski.parkingguidance.sharedkernel.domain.base.IdentifiableDomainObject;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "processed_remote_events")
public class ProcessedRemoteEvent implements IdentifiableDomainObject<String> {

    @Id
    @Column(name = "source", nullable = false)
    private String source;

    @Column(name = "last_processed_event_id", nullable = false)
    private long lastProcessedEventId;

    public ProcessedRemoteEvent() { }

    public ProcessedRemoteEvent(String source, Long lastProcessedEventId) {
        this.source = Objects.requireNonNull(source);
        this.lastProcessedEventId = lastProcessedEventId;
    }

    @NonNull
    public String source() {
        return source;
    }

    @NonNull
    public long lastProcessedEventId() {
        return lastProcessedEventId;
    }

    @Override
    public String id() {
        return source();
    }
}
