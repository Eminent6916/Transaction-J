package com.UserService.Seed;

import com.UserService.Model.Tier;
import com.UserService.Repository.TierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TierSeeder implements CommandLineRunner {

    private final TierRepository tierRepository;

    @Override
    public void run(String... args) {
        seedTier("Gold", BigDecimal.valueOf(50000), BigDecimal.valueOf(2000000), BigDecimal.valueOf(20000));
        seedTier("Diamond", BigDecimal.valueOf(500000), BigDecimal.valueOf(20000000), BigDecimal.valueOf(200000));
        seedTier("Ultimate", BigDecimal.valueOf(500000000), BigDecimal.valueOf(900000000), BigDecimal.valueOf(2000000));
    }

    private void seedTier(String name, BigDecimal daily, BigDecimal monthly, BigDecimal single) {
        if (tierRepository.findByNameIgnoreCase(name).isEmpty()) {
            Tier tier = Tier.builder()
                    .name(name)
                    .daily_limit(daily)
                    .monthly_limit(monthly)
                    .single_transaction_limit(single)
                    .build();

            tierRepository.save(tier);
            System.out.println("Seeded Tier: " + name);
        }
    }
}
