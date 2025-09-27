package com.TransactionService.Model;

import com.TransactionService.Constants.Messages;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class StoreLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = Messages.ACQUIRER_MISSING)
    private String acquirer;

    private String endpoint;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    private String status;
    private String response;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @PrePersist
    protected void onCreate(){
        created_at = LocalDateTime.now();
        updated_at = LocalDateTime.now();
    }
}
