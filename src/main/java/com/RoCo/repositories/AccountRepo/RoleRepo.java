package com.RoCo.repositories.AccountRepo;

import com.RoCo.entities.Account.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
}
