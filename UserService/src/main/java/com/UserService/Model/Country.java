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
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "iso2", length = 2)
    private String iso2;

    @Column(name = "iso3", length = 3)
    private String iso3;

    @Column(name = "phone_code", length = 10)
    private String phoneCode;

    @Column(name = "currency", length = 10)
    private String currency;

    @Column(name = "currency_name", length = 50)
    private String currencyName;

    @Column(name = "currency_symbol", length = 10)
    private String currencySymbol;

    @Column(name = "region", length = 50)
    private String region;

    @Column(name = "subregion", length = 50)
    private String subregion;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "emoji", length = 10)
    private String emoji;

    @Column(name = "emojiU", length = 20)
    private String emojiU;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

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

    public Country(String name, String iso2, String iso3, String phoneCode, String currency,
                   String currencyName, String currencySymbol, String region, String subregion,
                   Double latitude, Double longitude, String emoji, String emojiU) {
        this();
        this.name = name;
        this.iso2 = iso2;
        this.iso3 = iso3;
        this.phoneCode = phoneCode;
        this.currency = currency;
        this.currencyName = currencyName;
        this.currencySymbol = currencySymbol;
        this.region = region;
        this.subregion = subregion;
        this.latitude = latitude;
        this.longitude = longitude;
        this.emoji = emoji;
        this.emojiU = emojiU;
        this.active = true;
        this.isDeleted = false;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", iso2='" + iso2 + '\'' +
                ", iso3='" + iso3 + '\'' +
                ", phoneCode='" + phoneCode + '\'' +
                ", currency='" + currency + '\'' +
                ", region='" + region + '\'' +
                ", subregion='" + subregion + '\'' +
                ", active=" + active +
                '}';
    }
}