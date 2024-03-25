package com.RoCo.repositories.AccountRepo;

import com.RoCo.entities.Account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findFirstByName(String name);
    User findByNameAndPassword(String name, String password);
}
