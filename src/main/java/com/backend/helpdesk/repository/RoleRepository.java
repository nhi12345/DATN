package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.RoleEntity;
import com.backend.helpdesk.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByName(String name);
}
