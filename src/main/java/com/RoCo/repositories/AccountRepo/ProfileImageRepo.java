package com.RoCo.repositories.AccountRepo;

import com.RoCo.entities.Account.ProfileImageEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileImageRepo extends JpaRepository<ProfileImageEnt, Long> {
    ProfileImageEnt findFirstByImageId(Long id);
}
