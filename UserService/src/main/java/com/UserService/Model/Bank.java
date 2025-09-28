package com.UserService.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "banks")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "slug", nullable = false, unique = true, length = 255)
    private String slug;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Column(name = "longcode", length = 50)
    private String longcode;

    @Column(name = "gateway", length = 50)
    private String gateway;

    @Column(name = "pay_with_bank", nullable = false)
    private Boolean payWithBank = false;

    @Column(name = "supports_transfer", nullable = false)
    private Boolean supportsTransfer = true;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "country", nullable = false, length = 100)
    private String country = "Nigeria";

    @Column(name = "currency", nullable = false, length = 10)
    private String currency = "NGN";

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Add this constructor for your seeder
    public Bank(String name, String slug, String code, String longcode, String gateway,
                Boolean payWithBank, Boolean supportsTransfer, Boolean active,
                String country, String currency, Boolean isDeleted) {
        this.name = name;
        this.slug = slug;
        this.code = code;
        this.longcode = longcode;
        this.gateway = gateway;
        this.payWithBank = payWithBank != null ? payWithBank : false;
        this.supportsTransfer = supportsTransfer != null ? supportsTransfer : true;
        this.active = active != null ? active : true;
        this.country = country != null ? country : "Nigeria";
        this.currency = currency != null ? currency : "NGN";
        this.isDeleted = isDeleted != null ? isDeleted : false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", code='" + code + '\'' +
                ", longcode='" + longcode + '\'' +
                ", gateway='" + gateway + '\'' +
                ", payWithBank=" + payWithBank +
                ", supportsTransfer=" + supportsTransfer +
                ", active=" + active +
                ", country='" + country + '\'' +
                ", currency='" + currency + '\'' +
                ", isDeleted=" + isDeleted +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}