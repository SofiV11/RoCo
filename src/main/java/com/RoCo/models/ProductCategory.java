//package com.RoCo.models;
//
//import java.util.Objects;
//
//public class ProductCategory {
//    private Long id;
//    private String catName;
//    private String catLabel;
//
//    public ProductCategory(Long id, String catName, String catLabel) {
//        this.id = id;
//        this.catName = catName;
//        this.catLabel = catLabel;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof ProductCategory that)) return false;
//        return Objects.equals(id, that.id) && Objects.equals(catName, that.catName) && Objects.equals(catLabel, that.catLabel);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, catName, catLabel);
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getCatName() {
//        return catName;
//    }
//
//    public String getCatLabel() {
//        return catLabel;
//    }
//
//    @Override
//    public String toString() {
//        return "ProductCategory{" +
//                "id=" + id +
//                ", catName='" + catName + '\'' +
//                ", catLabel='" + catLabel + '\'' +
//                '}';
//    }
//}
