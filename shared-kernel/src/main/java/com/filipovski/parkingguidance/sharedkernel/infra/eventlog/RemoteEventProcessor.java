package com.filipovski.parkingguidance.sharedkernel.infra.eventlog;

import com.filipovski.parkingguidance.sharedkernel.domain.base.RemoteEventLog;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Map;

@Service
public class RemoteEventProcessor {
    private final Map<String, RemoteEventLogService> remoteEventLogServices;
    private final Map<String, RemoteEventTranslator> remoteEventTranslators;
    private final ProcessedRemoteEventRepository processedRemoteEventRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final TransactionTemplate transactionTemplate;

    public RemoteEventProcessor(Map<String, RemoteEventLogService> remoteEventLogServices,
                                Map<String, RemoteEventTranslator> remoteEventTranslators,
                                ProcessedRemoteEventRepository processedRemoteEventRepository,
                                ApplicationEventPublisher applicationEventPublisher,
                                TransactionTemplate transactionTemplate) {
        this.remoteEventLogServices = remoteEventLogServices;
        this.remoteEventTranslators = remoteEventTranslators;
        this.processedRemoteEventRepository = processedRemoteEventRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.transactionTemplate = transactionTemplate;
    }

    @Scheduled(fixedDelay = 10000)
    public void processEvents() {
        remoteEventLogServices.values().forEach(this::processEvents);
    }

    private void processEvents(@NonNull RemoteEventLogService remoteEventLogService) {
        RemoteEventLog log = remoteEventLogService.currentLog(getLastProcessedId(remoteEventLogService));
        processEvents(remoteEventLogService, log.events());
    }

    private Long getLastProcessedId(@NonNull RemoteEventLogService remoteEventLogService) {
        return processedRemoteEventRepository.findById(remoteEventLogService.source())
                .map(ProcessedRemoteEvent::lastProcessedEventId)
                .orElse(0L);
    }

    private void processEvents(@NonNull  RemoteEventLogService remoteEventLogService,
                               @NonNull  List<StoredDomainEvent> events) {
        events.forEach(event -> {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {

                @Override
                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                    publishEvent(event);
                    setLastProcessedId(remoteEventLogService, event.id());
                }
            });
        });
    }

    private void publishEvent(@NonNull StoredDomainEvent event) {
        remoteEventTranslators.values().stream()
                .filter(translator -> translator.supports(event))
                .findFirst()
                .flatMap(translator -> translator.translate(event))
                .ifPresent(applicationEventPublisher::publishEvent);
    }

    private void setLastProcessedId(@NonNull RemoteEventLogService remoteEventLogService, Long lastProcessedId) {
        processedRemoteEventRepository
                .saveAndFlush(new ProcessedRemoteEvent(remoteEventLogService.source(), lastProcessedId));
    }
}
