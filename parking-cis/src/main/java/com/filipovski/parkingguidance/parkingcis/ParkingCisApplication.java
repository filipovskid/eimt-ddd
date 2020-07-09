package com.filipovski.parkingguidance.parkingcis;

import com.filipovski.parkingguidance.sharedkernel.SharedConfiguration;
import com.filipovski.parkingguidance.sharedkernel.infra.eventlog.RemoteEventLogService;
import com.filipovski.parkingguidance.sharedkernel.infra.port.client.RemoteEventLogServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@EntityScan
@Import(SharedConfiguration.class)
@SpringBootApplication
public class ParkingCisApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingCisApplication.class, args);
	}

	@Bean
	public RemoteEventLogService parkingManagerEvents(@Value("${app.parking-manager.url}") String remoteServerUrl,
													  @Value("${app.parking-manager.connect-timeout-ms}") int connectTimeout,
													  @Value("${app.parking-manager.read-timeout-ms}") int readTimeout) {
		return new RemoteEventLogServiceClient(remoteServerUrl, connectTimeout, readTimeout);
	}

}
