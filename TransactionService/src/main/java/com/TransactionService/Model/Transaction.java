package com.TransactionService.Model;

import com.TransactionService.Constants.Messages;
import com.TransactionService.Constants.Transaction.Status;
import com.TransactionService.Constants.Transaction.Type;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotBlank(message = Messages.USER_ID_MISSING)
    private User user;


    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @NotBlank(message = Messages.AMOUNT_MISSING)
    @Column(precision = 11, scale = 2)
    private BigDecimal amount;
    private String reference;

    @Enumerated(EnumType.STRING)
    private Type transaction_type;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @PrePersist
    protected void onCreate(){
        created_at = LocalDateTime.now();
        updated_at = LocalDateTime.now();
    }

}
