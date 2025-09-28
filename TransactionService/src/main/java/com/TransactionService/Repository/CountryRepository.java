package com.TransactionService.Repository;

import com.TransactionService.Model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByNameIgnoreCase(String name);
    Optional<Country> findByIso2IgnoreCase(String iso2);
    Optional<Country> findByIso3IgnoreCase(String iso3);
    List<Country> findByActive(Boolean active);
    List<Country> findByRegionIgnoreCase(String region);
    List<Country> findByCurrencyIgnoreCase(String currency);
}