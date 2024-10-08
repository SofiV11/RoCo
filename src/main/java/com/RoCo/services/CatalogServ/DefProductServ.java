package com.RoCo.services.CatalogServ;


import com.RoCo.entities.Account.User;
import com.RoCo.entities.CatalogEnt.BucketEnt;
import com.RoCo.entities.CatalogEnt.ProductCatEnt;
import com.RoCo.entities.CatalogEnt.ProductEnt;
import com.RoCo.mappers.ProductToEntMapper;
import com.RoCo.models.Product;
import com.RoCo.exceptions.CatalogExc.ProductNotFoundException;
import com.RoCo.repositories.CatalogRepo.ProductCatRepo;
import com.RoCo.repositories.CatalogRepo.ProductRepo;
import com.RoCo.services.AccountServ.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


//////////
////5////
/////////

@Service //возлагаем на Spring создание объекта этого класса
// @RequiredArgsConstructor // генерирует конструктор с необходимыми аргументами (productRepo), но не сгенерил почему то
public class DefProductServ implements ProductServ{
    //private final ProductServ productServ;
    private final ProductRepo productRepo;
    private final ProductToEntMapper mapper;

    private final ProductCatRepo productCatRepo;

    private final BucketServ bucketServ;

    private final UserService userServ;


    public DefProductServ(ProductRepo productRepo, ProductToEntMapper mapper, ProductCatRepo productCatRepo, BucketServ bucketServ, UserService userServ) {
        this.productRepo = productRepo;
        this.mapper = mapper;
        this.productCatRepo = productCatRepo;
        this.bucketServ = bucketServ;
        this.userServ = userServ;
    }

    // without mapping
//    @Override
//    public Product getProductById(Long id){
//        ProductEnt productEnt = productRepo
//                .findById(id) // returns Optional<productEnt>
//                .orElseThrow( () -> new ProductNotFoundException("Product not found: id = " + id));
//        //значение из Optional, и, если оно отсутствует, бросает исключение, которое создается в переданном в качестве аргумента лямбда-выражении
//        return new Product( productEnt.getPk(),
//                            productEnt.getName(),
//                            productEnt.getCategory(),
//                            productEnt.getPrice(),
//                            productEnt.getImgUrl(),
//                            productEnt.getIsAvailable(),
//                            productEnt.getDescr()
//                           )
//                ;
//    }


    // with mapping
    @Override
    public Product getProductById(Long id){
        ProductEnt productEnt = productRepo
                .findById(id) // returns Optional<productEnt>
                .orElseThrow( () -> new ProductNotFoundException("Product not found: id = " + id));
        //значение из Optional, и, если оно отсутствует, бросает исключение, которое создается в переданном в качестве аргумента лямбда-выражении
        return mapper.productEntToProduct(productEnt);
    }


    // without mapping
//    @Override
//    public List<Product> getAllProducts(){
//        Iterable<ProductEnt> iterable = productRepo.findAll(); // returns Iterable<>
//        ArrayList<Product> products = new ArrayList<>();
//        for(ProductEnt productEnt : iterable){
//            products.add(new Product(productEnt.getPk(),
//                                     productEnt.getName(),
//                                     productEnt.getCategory(),
//                                     productEnt.getPrice(),
//                                     productEnt.getImgUrl(),
//                                     productEnt.getIsAvailable(),
//                                     productEnt.getDescr()
//                                    )
//                        );
//        }
//        return products;
//    }


    //With mapping
    @Override
    public List<Product> getAllProducts(){
        Iterable<ProductEnt> iterable = productRepo.findAll(); // returns Iterable<>
        ArrayList<Product> products = new ArrayList<>();
        for(ProductEnt productEnt : iterable){
            products.add(mapper.productEntToProduct(productEnt));
        }
        return products;
    }

    @Override
    public Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection){
        Sort sort =  sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending() :
                  Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo -1, pageSize, sort);
        Page<ProductEnt> paged = productRepo.findAll(pageable);

        List<Product> productsList = paged.stream()
                .map(mapper::productEntToProduct)
                .toList();

        Page<Product> products = new PageImpl<>(productsList, pageable, paged.getTotalElements());
        return products;
    }

    @Override
    public List<Product> getAllProductsPageable(Pageable pageable){
        Page<ProductEnt> iterable = productRepo.findAll(pageable); // returns Iterable<>
        ArrayList<Product> products = new ArrayList<>();
        for(ProductEnt productEnt : iterable){
            products.add(mapper.productEntToProduct(productEnt));
        }
        return products;
    }


//    public Page<Product> getAllProductsP(){
//        Iterable<ProductEnt> iterable = productRepo.findAll(); // returns Iterable<>
//        Page<Product> products = new Page<>();
//        for(ProductEnt productEnt : iterable){
//            products.(mapper.productEntToProduct(productEnt));
//        }
//        return products;
//    }





//    // only with mapping
//    @Override
//    public List<Product> getByCategory(String category) {
//        return null;
//    }


    // without mapping
//    @Override
//    public void addProduct(Product product) {
//        ProductEnt productEnt = new ProductEnt(null,
//                                               product.getName(),
//                                               product.getCategory(),
//                                               product.getPrice(),
//                                               product.getImgUrl(),
//                                               product.getIsAvailable(),
//                                               product.getDescr()
//                );
//        productRepo.save(productEnt);
//    }


    // with mapping
    @Override
    public void addProduct(Product product) {
        //ProductEnt productEnt = mapper.productToProductEnt(product);
        productRepo.save(productToProductEnt(product));
    }



    // only with mapping
    @Override
    public void editProduct(Product product) {

    }

    @Override
    public List<ProductCatEnt> getAllCategories() {
        Iterable<ProductCatEnt> iterable = productCatRepo.findAll(); // returns Iterable<>
        ArrayList<ProductCatEnt> categories = new ArrayList<>();
        for(ProductCatEnt productCatEnt : iterable){
            categories.add(productCatEnt);
        }
        return categories;
    }

    @Override
    public void addNewCategory(String name, String label) {
        productCatRepo.save(new ProductCatEnt(null, name, label));
    }

    @Override
    public ProductCatEnt getCatEntById(Long id) {
        return productCatRepo
                .findById(id) // returns Optional<productEnt>
                .orElseThrow( () -> new ProductNotFoundException("Category not found: id = " + id));

    }

//    @Override
//    public Long getCatIdByLabel(String label) {
////        ProductCatEnt productCatEnt = productCatRepo
////                .findById(id) // returns Optional<productEnt>
////                .orElseThrow( () -> new ProductNotFoundException("Category not found: id = " + id));
////        return productCatEnt.getId();
//        return null;
//    }

    @Override
    public ProductCatEnt getCatEntByLabel(String label) {
        return productCatRepo
                .findByLabel(label) // returns Optional<productEnt>
         ;
    }

    @Override
    public List<Product> getPresentProducts() {
        return getAllProducts().stream()
                .filter( p -> p.getPrice() > 50 && p.getIsAvailable())
                .limit(12)
                .toList();
    }

    @Override
    public List<Product> findByCategory(Long category) {
        Iterable<ProductEnt> iterable = productRepo.findAllByCategoryId(getCatEntById(category) );
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(mapper::productEntToProduct)
                .collect(Collectors.toList());
    }

    public ProductEnt productToProductEnt(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductEnt productEnt = new ProductEnt();

        productEnt.setPk( product.getPk() );
        productEnt.setName( product.getName() );
        productEnt.setCategoryId( getCatEntByLabel(product.getCategory()) );
        productEnt.setPrice( product.getPrice() );
        productEnt.setImgUrl( product.getImgUrl() );
        productEnt.setAvailable( product.getIsAvailable() );
        productEnt.setDescr( product.getDescr() );

        return productEnt;
    }

    public void addToUserBucket(Long productId, String username ){
        User user = userServ.findByUsername(username);
        if (user == null){
            throw new RuntimeException("User not found");
        }

        BucketEnt bucket = user.getBucket();
        if(bucket==null){
            BucketEnt newBucket = bucketServ.createBucket(user, Collections.singletonList(productId));
            user.setBucket(newBucket);
            userServ.save(user);
        } else {
            bucketServ.addProducts(bucket, Collections.singletonList(productId));
        }
    }

//    @Override
//    public Page<Product> findByCategoryIdAndNameContaining(String name, ProductCatEnt category, Sort sort) {
//        return (Page<Product>) productRepo.findByCategoryIdAndNameContaining(name, category, sort)
//                .stream()
//                .map(mapper::productEntToProduct);
//    }

}
