package com.RoCo.controllers;

import com.RoCo.entities.CatalogEnt.ProductCatEnt;
import com.RoCo.models.Product;
import com.RoCo.services.CatalogServ.ProductServ;
import com.RoCo.utils.AParser;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ProductServ productServ;

    @GetMapping({"/", ""}) // обработка определенного url адреса (в данном случае главная стр)
    public String mainPage(@NotNull Model model){//обязательный параметр

        List<Product> products = productServ.getPresentProducts();
        model.addAttribute("products", products.stream());
            //model.addAttribute("products2", products.stream().filter())
        model.addAttribute("title", "MainPage"); // th:... = " ${title} "

        String anekText;
        try{
            Document webPage = AParser.getPageA();
            if (webPage != null) anekText = AParser.getAnekBody(webPage);
            else anekText = "";
        } catch (IOException e) {
            anekText = "data not found";
            //throw new RuntimeException(e);
        }
        model.addAttribute("anek", anekText);
        return "MainPage/mainPage.html"; // вызов шаблона
    }



}
