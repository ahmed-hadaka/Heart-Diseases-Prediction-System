package com.graduationproject.heartdiseasepredictionsystem.repository;

import com.graduationproject.heartdiseasepredictionsystem.model.Role;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleName);
}
