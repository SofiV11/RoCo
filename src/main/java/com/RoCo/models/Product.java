package com.RoCo.models;

import lombok.Value;

import java.util.Objects;


//////////
////1////
/////////

//Book — это класс сервисного слоя, а BookEntity — DAO
//@Value
public class Product {
    public Long getPk() {
        return pk;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public String getDescr() {
        return descr;
    }

    private final Long pk;
    private final String name;
    private final String category;
    private final Double price;
    private final String imgUrl;
    private final Boolean isAvailable;
    private final String descr;

    public Product(Long pk, String name, String category, Double price, String imgUrl, Boolean isAvailable, String descr) {
        this.pk = pk;
        this.name = name;
        this.category = category;
        this.price = price;
        this.imgUrl = imgUrl;
        this.isAvailable = isAvailable;
        this.descr = descr;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pk=" + pk +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", imgUrl='" + imgUrl + '\'' +
                ", isAvailable=" + isAvailable +
                ", descr='" + descr + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(pk, product.pk) && Objects.equals(name, product.name) && Objects.equals(category, product.category) && Objects.equals(price, product.price) && Objects.equals(imgUrl, product.imgUrl) && Objects.equals(isAvailable, product.isAvailable) && Objects.equals(descr, product.descr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, name, category, price, imgUrl, isAvailable, descr);
    }
}
