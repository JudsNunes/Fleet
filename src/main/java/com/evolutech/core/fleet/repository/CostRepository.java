package com.evolutech.core.fleet.repository;

import com.evolutech.core.fleet.model.entity.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostRepository extends JpaRepository<Cost, Long> {

}
