package com.RoCo.entities.CatalogEnt;

import jakarta.persistence.*;
import lombok.Data;


//////////
////2////
/////////


@Entity //JPA
@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Table(name="products") //JPA
public class ProductEnt {
    @Id //JPA
    @GeneratedValue(strategy = GenerationType.AUTO) //JPA
    private Long pk;
    private String name;

    @ManyToOne
    @JoinColumn(name="category_id")
    private ProductCatEnt categoryId;

    private Double price;
    private String imgUrl;
    private Boolean isAvailable;
    private String descr;

    public ProductEnt() {
    }

    public ProductEnt(Long pk, String name, ProductCatEnt categoryId, Double price, String imgUrl, Boolean isAvailable, String descr) {
        this.pk = pk;
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.imgUrl = imgUrl;
        this.isAvailable = isAvailable;
        this.descr = descr;
    }

    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCatEnt getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(ProductCatEnt categoryId) {
        this.categoryId = categoryId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
//Integer stockQuant;

}
