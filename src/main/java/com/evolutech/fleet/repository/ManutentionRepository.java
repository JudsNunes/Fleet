package com.evolutech.fleet.repository;

import com.evolutech.fleet.model.entity.Manutetion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManutentionRepository extends JpaRepository<Manutetion, Long> {
}
