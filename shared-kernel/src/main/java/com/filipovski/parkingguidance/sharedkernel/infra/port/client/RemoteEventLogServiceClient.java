package com.filipovski.parkingguidance.sharedkernel.infra.port.client;

import com.filipovski.parkingguidance.sharedkernel.domain.base.RemoteEventLog;
import com.filipovski.parkingguidance.sharedkernel.infra.eventlog.RemoteEventLogService;
import com.filipovski.parkingguidance.sharedkernel.infra.eventlog.StoredDomainEvent;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.NonNull;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RemoteEventLogServiceClient implements RemoteEventLogService {
    private final String source;
    private final String remoteServerUrl;
    private final WebClient webClient;

    public RemoteEventLogServiceClient(@NonNull String remoteServerUrl,
                                       int connectTimeout, int readTimeout ) {
        this.source = remoteServerUrl; // Used for ProcessedRemoteEvent
        this.remoteServerUrl = remoteServerUrl;
        this.webClient = createWebClient(connectTimeout, readTimeout);
    }

    private WebClient createWebClient(int connectTimeout, int readTimeout) {
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS));
                });

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }

    @NonNull
    @Override
    public String source() {
        return source;
    }

    @Override
    public RemoteEventLog currentLog(Long lastProcessedId) {
        URI logUri = UriComponentsBuilder.fromUriString(remoteServerUrl)
                .path(String.format("/api/event-log/%d", lastProcessedId)).build().toUri();

        return retrieveLog(logUri);
    }

    private RemoteEventLog retrieveLog(@NonNull URI uri) {
        List<StoredDomainEvent> storedDomainEvents = webClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse ->
                    Mono.error(new IllegalArgumentException("Could not retrieve log from URI " + uri))
                )
                .bodyToMono(new ParameterizedTypeReference<List<StoredDomainEvent>>() {})
                .block();

        return new RemoteEventLogImpl(storedDomainEvents);
    }

    class RemoteEventLogImpl implements  RemoteEventLog {
        private final List<StoredDomainEvent> events;

        public RemoteEventLogImpl(List<StoredDomainEvent> events) {
            this.events = events;
        }

        @Override
        public List<StoredDomainEvent> events() {
            return events;
        }
    }

}
