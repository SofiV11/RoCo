package com.RoCo.models;
import lombok.Data;


// класс запроса/data transfer object
@Data
public class ProductRequest {
    private String category;
    private String name;
    private Double price;
}

//
//package com.example.bookstore.model;
//
//import lombok.Data;
//
//@Data
//public class BookRequest {
//    private String author;
//    private String title;
//    private Double price;
//}