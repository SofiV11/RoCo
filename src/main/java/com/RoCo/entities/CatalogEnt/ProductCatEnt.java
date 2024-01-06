package com.RoCo.entities.CatalogEnt;

import com.RoCo.models.Product;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="product_categories")
public class ProductCatEnt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String label;

    @OneToMany(mappedBy = "categoryId")
    private List<ProductEnt> productsOfCat;

    public ProductCatEnt(Long id, String name, String label) {
        this.id = id;
        this.name = name;
        this.label = label;
    }

    public ProductCatEnt() {
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLabel(String label) {
        this.label = label;
    }


}
