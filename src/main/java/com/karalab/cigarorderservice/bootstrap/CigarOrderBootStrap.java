package com.karalab.cigarorderservice.bootstrap;

import com.karalab.cigarorderservice.domain.Customer;
import com.karalab.cigarorderservice.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class CigarOrderBootStrap implements CommandLineRunner {

    public static final String TASTING_ROOM = "Tasting Room";
    public static final String CIGAR_1_EAN = "0631234200036";
    public static final String CIGAR_2_EAN = "0631234300019";
    public static final String CIGAR_3_EAN = "0083783375213";

    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadCustomerData();
    }

    private void loadCustomerData() {
        if (customerRepository.count() == 0) {
            customerRepository.save(Customer.builder()
                    .customerName(TASTING_ROOM)
                    .apiKey(UUID.randomUUID())
                    .build());
        }
    }
}
