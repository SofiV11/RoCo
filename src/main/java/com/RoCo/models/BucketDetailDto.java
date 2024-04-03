package com.RoCo.models;

import com.RoCo.entities.CatalogEnt.ProductEnt;

public class BucketDetailDto {
    private String title;
    private Long productId;
    private Double price;
    private Integer amount;
    private Double sum;
    private String imgUrl;

    public BucketDetailDto(ProductEnt product){
        this.title = product.getName();
        this.productId = product.getPk();
        this.price = product.getPrice();
        this.amount = 1;
        this.sum = product.getPrice() * amount;
        this.imgUrl = product.getImgUrl();
    }

    public String getTitle() {
        return title;
    }

    public Long getProductId() {
        return productId;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }

    public Double getSum() {
        return sum;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
