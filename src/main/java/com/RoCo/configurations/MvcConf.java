package com.RoCo.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConf implements WebMvcConfigurer {
    @Value("${upload.path}")
    private String uploadPath;
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/login").setViewName("Account");
        //registry.addViewController("/MyAccount").setViewName("MyAcc");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///" + uploadPath + "/");
    }

}


//Если метод с аннотацией @RequestMapping отображен на URL-адрес для любого
//HTTP-метода, то контроллер представления нельзя использовать для
//обработки того же URL-адреса. Это связано с тем, что совпадение по
// URL-адресу с аннотированным контроллером считается достаточно
//убедительным признаком принадлежности конечной точки, поэтому клиенту
//может быть отправлен ответ 405 (METHOD_NOT_ALLOWED), 415 (UNSUPPORTED_MEDIA_TYPE)
//или аналогичный ответ для помощи в отладке. По этой причине рекомендуется избегать
//разделения обработки URL-адреса на аннотированный контроллер и контроллер представления.