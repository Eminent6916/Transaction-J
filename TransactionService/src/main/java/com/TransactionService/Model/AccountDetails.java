package com.TransactionService.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "account_details",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_accountdetails_phone", columnNames = "phone"),
                @UniqueConstraint(name = "uk_accountdetails_bvn", columnNames = "bvn")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "middle_name", length = 100)
    private String middleName;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @Column(name = "pin", length = 6)
    private String pin;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "bvn", length = 11, unique = true)
    private String bvn;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tier_id", nullable = false)
    private Tier tier;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        // Set both timestamps when the entity is first saved
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        // Optional: Keep the default Tier logic here if needed
        // if (this.tier == null) {
        //     this.tier = Tier.builder().id(1).build();
        // }
    }

    @PreUpdate
    protected void onUpdate() {
        // Update only the updatedAt timestamp before an update operation
        this.updatedAt = LocalDateTime.now();
    }

}
