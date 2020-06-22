package com.filipovski.parkingguidance.sharedkernel.infra.eventlog;

import com.filipovski.parkingguidance.sharedkernel.domain.base.RemoteEventLog;

public interface RemoteEventLogService {
    String source();

    RemoteEventLog currentLog(Long lastProcessedId);
}
