package com.RoCo.repositories.AccountRepo;

import com.RoCo.entities.Account.User;
import com.RoCo.entities.Account.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findFirstByName(String name);
    User findByNameAndPassword(String name, String password);

    List<User> findAllByRoleNot(UserRole roleException);
}
