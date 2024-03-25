package com.RoCo.repositories.CatalogRepo;

import com.RoCo.entities.CatalogEnt.ProductCatEnt;
import com.RoCo.entities.CatalogEnt.ProductEnt;
import org.apache.catalina.CredentialHandler;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductCatRepo extends CrudRepository <ProductCatEnt, Long>{
    ProductCatEnt findByLabel(String label);
}
