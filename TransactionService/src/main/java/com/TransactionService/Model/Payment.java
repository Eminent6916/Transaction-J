package com.TransactionService.Model;

import com.TransactionService.Constants.Messages;
import com.TransactionService.Constants.Transaction.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotBlank(message = Messages.USER_ID_MISSING)
    private User user;

    private String reference;
    @NotBlank(message = Messages.ACCOUNT_NAME_MISSING)
    private String account_name;
    @NotBlank(message = Messages.ACCOUNT_NUM_MISSING)
    private String account_number;
    @NotBlank(message = Messages.BANK_NAME_MISSING)
    private String bank_name;
    @NotBlank(message = Messages.AMOUNT_MISSING)

    private String bank_sort_code;
    @Column(precision = 11, scale = 2)
    private BigDecimal amount;
    @Column(precision = 11, scale = 2)
    private BigDecimal  charge;
    @Column(precision = 11, scale = 2)
    private BigDecimal  total_amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private String transfer_purpose;
    private String failure_reason;
    private String acquirer;
    private String report;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        status = PaymentStatus.PENDING;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }



}
