package com.filipovski.parkingguidance.parkingmanager;

import com.filipovski.parkingguidance.sharedkernel.SharedConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan
@Import(SharedConfiguration.class)
public class ParkingManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingManagerApplication.class, args);
    }

}
