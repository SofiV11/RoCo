package com.RoCo.entities.CatalogEnt;

//import com.RoCo.entities.Account.SiteUser;
import com.RoCo.entities.Account.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name =  "buckets")
public class BucketEnt {
    private static final String SEQ_NAME = "bucket_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name=SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long bucketId;

    @OneToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    //private SiteUser user;
    private User user;

    @ManyToMany
    @JoinTable(name = "buckets_products"
            , joinColumns = @JoinColumn(name = "bucket_id")
            , inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductEnt> products;


    public Long getBucketId() {
        return bucketId;
    }

    public User getUser() {
        return user;
    }

    public List<ProductEnt> getProducts() {
        return products;
    }

    public void setBucketId(Long bucketId) {
        this.bucketId = bucketId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProducts(List<ProductEnt> products) {
        this.products = products;
    }
}
