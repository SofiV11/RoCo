package com.RoCo.repositories.CatalogRepo;

import com.RoCo.entities.CatalogEnt.BucketEnt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketRepo extends JpaRepository<BucketEnt, Long> {

    //BucketEnt findById(Long id);
}
