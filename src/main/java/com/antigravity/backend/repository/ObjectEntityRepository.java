package com.antigravity.backend.repository;

import com.antigravity.backend.entity.ObjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectEntityRepository extends JpaRepository<ObjectEntity, Long> {
    List<ObjectEntity> findBySimulationId(Long simulationId);
}
