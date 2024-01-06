package com.RoCo.controllers;


import com.RoCo.entities.CatalogEnt.ProductCatEnt;
import com.RoCo.mappers.ProductToDtoMapper;
import com.RoCo.models.Product;
import com.RoCo.services.CatalogServ.ProductServ;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
//@RequestMapping("/catalog")
//@RequiredArgsConstructor
public class CatalogController {
    @Autowired
    private ProductServ productServ;

//    private final ProductToDtoMapper mapper;
//
//    public CatalogController(ProductServ productServ, ProductToDtoMapper mapper) {
//        this.productServ = productServ;
//        this.mapper = mapper;
//    }

    @GetMapping("/Catalog/detail{id}")
    public String ProductDetail(@PathVariable Long id, Model model){
        Product product = productServ.getProductById(id);
        model.addAttribute("product", product);
        return "CatalogPage/productDetail.html";
    }
    @GetMapping("/Catalog")
    public String CatalogPAge(Model model){
        List<Product> products= productServ.getAllProducts();
        List<ProductCatEnt> categories= productServ.getAllCategories(); //List<String> getAllCategories()
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("catlabel", null);
        return "CatalogPage/productList.html";
    }
    @GetMapping("/Catalog/category{cat_id}")
    public String CatalogPAgeCateg( @PathVariable Long cat_id, Model model){
        List<Product> products = productServ.findByCategory(cat_id);
        List<ProductCatEnt> categories= productServ.getAllCategories(); //List<String> getAllCategories()
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("catlabel", productServ.getCatEntById(cat_id).getLabel());
        return "CatalogPage/productList.html";
    }




}
