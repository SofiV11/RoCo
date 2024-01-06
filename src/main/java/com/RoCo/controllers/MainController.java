package com.RoCo.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @GetMapping("/") // обработка определенного url адреса (в данном случае главная стр)
    public String mainPage(@NotNull Model model){//обязательный параметр
        model.addAttribute("title", "MainPage"); // th:... = " ${title} "
        return "MainPage/mainPage.html"; // вызов шаблона
    }




}
