package com.UserService.Model;

import com.UserService.Constants.Messages;
import com.UserService.Constants.Transaction.Status;
import com.UserService.Constants.Transaction.Type;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
    @CreatedDate
    private LocalDateTime created_at;
    @LastModifiedDate
    private LocalDateTime updated_at;


}
