package com.RoCo.repositories.CatalogRepo;

import com.RoCo.entities.CatalogEnt.ProductCatEnt;
import com.RoCo.entities.CatalogEnt.ProductEnt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


//////////
////3////
/////////


@Repository
public interface ProductRepo extends JpaRepository<ProductEnt, Long> {
    List<ProductEnt> findAllByCategoryId(ProductCatEnt category);
    //List<ProductEnt> findAllByCategory(ProductCatEnt category);
    //List<ProductEnt> findAllByCategory(Long id);
   /// Page<ProductEnt> findByCategoryIdAndNameContaining(String name, ProductCatEnt category, Sort sort);



    //findByPk(Long pk); // finds object
    //getOne(Long pk) finds object's link

}
