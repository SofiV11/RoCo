package com.RoCo.services.CatalogServ;

import com.RoCo.entities.Account.User;
import com.RoCo.entities.CatalogEnt.BucketEnt;
import com.RoCo.entities.CatalogEnt.ProductEnt;
import com.RoCo.models.BucketDetailDto;
import com.RoCo.models.BucketDto;
import com.RoCo.models.Product;
import com.RoCo.repositories.CatalogRepo.BucketRepo;
import com.RoCo.repositories.CatalogRepo.ProductRepo;
import com.RoCo.services.AccountServ.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@Service
public class BucketServ {

    BucketRepo bucketRepo;
    ProductRepo productRepo;

    UserService userServ;

    @PersistenceContext
    private EntityManager em;

    public BucketServ(BucketRepo bucketRepo, ProductRepo productRepo, UserService userServ, EntityManager em) {
        this.bucketRepo = bucketRepo;
        this.productRepo = productRepo;
        this.userServ = userServ;
        this.em = em;
    }

    @Transactional
    BucketEnt createBucket(User user, List<Long> productsIds){
        BucketEnt bucket = new BucketEnt();
        bucket.setUser(user);
        List<ProductEnt> productList = getCollectRefProductsByIds(productsIds);
        bucket.setProducts(productList);
        return bucketRepo.save(bucket);
    }

    private List<ProductEnt> getCollectRefProductsByIds(List<Long> productsIds) {
        return productsIds.stream()
                .map(productRepo::getOne)
                .collect(Collectors.toList());
    }

    public void addProducts(BucketEnt bucket, List<Long> productIds){
        List<ProductEnt> products = bucket.getProducts();
        List<ProductEnt> newProductList = products==null ? new ArrayList<>() : new ArrayList<>(products);
        newProductList.addAll(getCollectRefProductsByIds(productIds));
        bucket.setProducts(newProductList);
        bucketRepo.save(bucket);

    }

//    public Integer getCountProductsInBucket(Principal principal) throws RuntimeException{
//        Long bucketId = userServ.findByUsername(principal.getName()).getBucket().getBucketId();
//        if (bucketId == null) return 0;
//            return em.createQuery("""
//                            SELECT count(bp.*) FROM buckets_products bp
//                            WHERE bp.bucket_id = :pBucketId""", Integer.class)
//                    .setParameter("pBucketId", bucketId).getSingleResult();
//
//    }

    public BucketDto getBucketByUSer(String name){
        User user = userServ.findByUsername(name);
        if(user==null || user.getBucket() == null){
            return new BucketDto();
        }

        BucketDto bucketDto = new BucketDto();
        Map<Long, BucketDetailDto> mapByProductId = new HashMap<>();

        List<ProductEnt> products = user.getBucket().getProducts();
        for(ProductEnt product : products){
            BucketDetailDto detail = mapByProductId.get(product.getPk());
            if(detail==null){
                mapByProductId.put(product.getPk(), new BucketDetailDto(product));
            } else {
                detail.setAmount(detail.getAmount() + 1 );
                detail.setSum(detail.getSum() + product.getPrice());
            }
        }
        bucketDto.setBucketDetails(new ArrayList<>(mapByProductId.values()));
        bucketDto.aggregate();

        return bucketDto;
    }

}

