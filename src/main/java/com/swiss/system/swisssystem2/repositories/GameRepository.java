package com.swiss.system.swisssystem2.repositories;

import com.swiss.system.swisssystem2.dtos.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<PlayerEntity, Long> {
}
