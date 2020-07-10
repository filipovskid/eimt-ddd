package com.filipovski.parkingguidance.parkingcontrol.domain.repository;

import com.filipovski.parkingguidance.parkingcontrol.domain.model.Ramp;
import com.filipovski.parkingguidance.parkingcontrol.domain.model.RampId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RampRepository extends JpaRepository<Ramp, RampId> {
}
