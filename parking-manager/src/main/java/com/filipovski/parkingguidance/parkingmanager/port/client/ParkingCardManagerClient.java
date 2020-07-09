package com.filipovski.parkingguidance.parkingmanager.port.client;

import com.filipovski.parkingguidance.parkingmanager.application.ParkingCardManager;
import com.filipovski.parkingguidance.parkingmanager.application.dto.ParkingCardDto;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingCard;
import com.filipovski.parkingguidance.parkingmanager.domain.model.ParkingCardId;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ParkingCardManagerClient implements ParkingCardManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingCardManagerClient.class);

    private final String serverUrl;
    private final WebClient webClient;

    public ParkingCardManagerClient(@Value("${app.parking-cis.url}") String serverUrl,
                                    @Value("${app.parking-cis.connect-timeout-ms}") int connectTimeout,
                                    @Value("${app.parking-cis.read-timeout-ms}") int readTimeout) {
        this.serverUrl = serverUrl;
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

    private UriComponentsBuilder uri() {
        return UriComponentsBuilder.fromUriString(serverUrl);
    }

    @Override
    public Optional<ParkingCard> findById(ParkingCardId parkingCardId) {
        ParkingCard parkingCard = webClient.get()
                .uri(uri().path("/api/cards/" + parkingCardId.getId()).build().toUri())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> {
//                    LOGGER.error("Error retrieving parking card by id", clientResponse);
                    return Mono.empty();
                })
                .bodyToMono(ParkingCard.class)
                .block();

        return Optional.of(parkingCard);
    }
}
