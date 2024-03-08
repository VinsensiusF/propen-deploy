package com.unimate.unimate.repository;

import com.unimate.unimate.entity.Role;
import com.unimate.unimate.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(RoleEnum name);
}
