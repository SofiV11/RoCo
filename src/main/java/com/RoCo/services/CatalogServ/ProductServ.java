package com.RoCo.services.CatalogServ;

import com.RoCo.entities.CatalogEnt.ProductCatEnt;
import com.RoCo.entities.CatalogEnt.ProductEnt;
import com.RoCo.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


//////////
////4////
/////////



public interface ProductServ {
    Product getProductById(Long id);
    List<Product> getAllProducts();
    Page<Product> findPaginated(int pageNo, int pageSize);
    List<Product> getAllProductsPageable(Pageable pageable);
    List<Product> findByCategory(Long category);
    void addProduct(Product product);
    void editProduct(Product product);
    List<ProductCatEnt> getAllCategories();
    void addNewCategory(String name, String label);
    ProductCatEnt getCatEntById(Long id);
    ProductCatEnt getCatEntByLabel(String label);

    List<Product> getPresentProducts();

    void addToUserBucket(Long productId, String username);

//    Page<Product> findByCategoryIdAndNameContaining(String name, ProductCatEnt category, Sort sort);

}
