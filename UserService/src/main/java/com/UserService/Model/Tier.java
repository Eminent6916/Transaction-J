package com.UserService.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;




@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(precision = 11, scale = 2)
    private BigDecimal daily_limit;

    @Column(precision = 11, scale = 2)
    private BigDecimal monthly_limit;

    @Column(precision = 11, scale = 2)
    private BigDecimal single_transaction_limit;

    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;

    @Builder
    public Tier(String name,
                BigDecimal daily_limit,
                BigDecimal monthly_limit,
                BigDecimal single_transaction_limit) {
        this.name = name;
        this.daily_limit = daily_limit;
        this.monthly_limit = monthly_limit;
        this.single_transaction_limit = single_transaction_limit;
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }
}

