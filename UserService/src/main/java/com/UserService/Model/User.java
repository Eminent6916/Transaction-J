package com.UserService.Model;

import com.UserService.Constants.Messages;
import com.UserService.Constants.Status;
import com.UserService.Constants.UserRole;
import com.UserService.Constants.VerificationStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(
        indexes = {
                @Index(name = "idx_user_email", columnList = "email"),
                @Index(name = "idx_user_uuid", columnList = "uuid")
        }
)
@Where(clause = "deleted_at IS NULL") // Soft delete filter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String uuid;

    @NotBlank(message = Messages.MAIL_REQUIRED)
    @Email(message = Messages.INVALID_MAIL)
    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(name = "verification_token")
    private String verificationToken;

    private LocalDateTime verificationTokenExpiry;

    @Column(nullable = false)
    private Boolean isMailVerified = Boolean.FALSE;

    @Column(nullable = false)
    private Boolean isOnboardingComplete = Boolean.FALSE;

    @Column(nullable = false)
    private Boolean isPinCreated = Boolean.FALSE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    private String identityType;
    private String identity;

    @ManyToOne
    @JoinColumn(name = "tier_id")
    private Tier tier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ENABLE;

    @Column(precision = 11, scale = 2, nullable = false)
    private BigDecimal dailyTransactionLimit = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)

    @Column(nullable = false)
    private UserRole role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private AccountDetails accountDetails;

    private String deviceToken;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
    }

    public void setAccountDetails(AccountDetails details) {
        this.accountDetails = details;
        if (details != null) {
            details.setUser(this);
        }
    }

}
