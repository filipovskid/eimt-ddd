package com.filipovski.parkingguidance.parkingcontrol;

import com.filipovski.parkingguidance.parkingcontrol.domain.model.Ramp;
import com.filipovski.parkingguidance.parkingcontrol.domain.model.RampStatus;
import com.filipovski.parkingguidance.parkingcontrol.domain.repository.RampRepository;
import com.filipovski.parkingguidance.sharedkernel.domain.card.CardCredit;
import com.filipovski.parkingguidance.sharedkernel.domain.person.FullName;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataGenerator {
    private final RampRepository rampRepository;

    public DataGenerator(RampRepository rampRepository) {
        this.rampRepository = rampRepository;
    }

    @PostConstruct
    @Transactional
    public void generateData() {
        if(rampRepository.findAll().size() == 0) {
            List<Ramp> ramps = new ArrayList<Ramp>();

            ramps.add(new Ramp(RampStatus.OPENED));
            ramps.add(new Ramp(RampStatus.OPENED));
            ramps.add(new Ramp(RampStatus.OPENED));

            rampRepository.saveAll(ramps);
        }
    }
}