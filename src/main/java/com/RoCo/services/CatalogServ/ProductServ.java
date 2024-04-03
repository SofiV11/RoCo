package com.RoCo.services.CatalogServ;

import com.RoCo.entities.CatalogEnt.ProductCatEnt;
import com.RoCo.entities.CatalogEnt.ProductEnt;
import com.RoCo.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;


//////////
////4////
/////////



public interface ProductServ {
    Product getProductById(Long id);
    List<Product> getAllProducts();
    List<Product> findByCategory(Long category);
    void addProduct(Product product);
    void editProduct(Product product);
    List<ProductCatEnt> getAllCategories();
    void addNewCategory(String name, String label);
    ProductCatEnt getCatEntById(Long id);
    ProductCatEnt getCatEntByLabel(String label);

    List<Product> getPresentProducts();

    void addToUserBucket(Long productId, String username);

}
