package com.RoCo.repositories.CatalogRepo;

import com.RoCo.entities.CatalogEnt.ProductCatEnt;
import com.RoCo.entities.CatalogEnt.ProductEnt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


//////////
////3////
/////////


@Repository
public interface ProductRepo extends CrudRepository<ProductEnt, Long> {
    List<ProductEnt> findAllByCategoryId(ProductCatEnt category);
    //List<ProductEnt> findAllByCategory(ProductCatEnt category);
    //List<ProductEnt> findAllByCategory(Long id);
}
