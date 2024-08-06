package com.RoCo.repositories.AccountRepo;

import com.RoCo.entities.Account.User;
import com.RoCo.entities.Account.UserDetailsEnt;
import com.RoCo.entities.Account.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetailsEnt, Long> {

    UserDetailsEnt findFirstByUser(User user);

    UserDetailsEnt findFirstByActivationCode(String code);

}
