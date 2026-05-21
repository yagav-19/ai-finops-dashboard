package com.finops.backend.repository;

import com.finops.backend.model.CloudCost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CloudCostRepository
        extends JpaRepository<CloudCost, Long> {

}