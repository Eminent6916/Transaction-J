package com.UserService.Repository;

import com.UserService.Model.Tier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TierRepository extends JpaRepository<Tier, Integer> {

    Optional<Tier> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}