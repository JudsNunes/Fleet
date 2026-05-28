package com.evolutech.core.fleet.repository;

import com.evolutech.core.fleet.model.entity.Manutention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManutentionRepository extends JpaRepository<Manutention, Long> {
}
