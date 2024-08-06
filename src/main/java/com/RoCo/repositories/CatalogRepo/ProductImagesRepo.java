package com.RoCo.repositories.CatalogRepo;


import com.RoCo.entities.CatalogEnt.ProductImagesEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImagesRepo extends JpaRepository<ProductImagesEnt, Long> {

}
