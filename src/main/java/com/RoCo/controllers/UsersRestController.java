package com.RoCo.controllers;

import com.RoCo.entities.Account.ProfileImageEnt;
import com.RoCo.entities.Account.User;
import com.RoCo.entities.Account.UserDetailsEnt;
import com.RoCo.services.AccountServ.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UsersRestController {
    @Autowired
    private UserService userService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @GetMapping("/profileImages/{id}")
    private ResponseEntity<?> getImageById(@PathVariable(value="id", required = false) Long id){
        if(id!=null) {
            ProfileImageEnt image = userService.findUserImageById(id);
            return ResponseEntity.ok()
                    .header("fileName", image.getOriginalFileNAme())
                    .contentType(MediaType.valueOf(image.getContentType()))
                    .contentLength(image.getSize())
                    .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
        } else return null;
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        //UserDetailsEnt userDetails = user.getUserDetails();

        //Map<String, Object> response = new HashMap<>();
        //response.put("user", user);
        //response.put("userDetails", userDetails);

//
//        if (userDetails !=null) {
//            if (userDetails.getImage() != null &&
//                    (userDetails.getImage().getSize() != null && userDetails.getImage().getSize() != 0)) {
//                response.put("imageId", userDetails.getImage().getImageId());
//            }
//            response.put("dateBirth", userDetails.getDateBirth());
//        }


        return ResponseEntity.ok(user);
    }

//    @GetMapping("/api/users/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable Long id) {
//        User user = userService.findUserById(id);
//        return ResponseEntity.ok(user);
//    }

    @PostMapping("/renderProfileForm")
//    @PreAuthorize("isAuthenticated()")
/*    @CrossOrigin(origins = "http://localhost:8080")*/
    public String renderProfileForm(@RequestBody User user) {
        //ModelMap modelMap = new ModelMap();
        Context context = new Context();
        context.setVariable("user", user);

        UserDetailsEnt userDetails = user.getUserDetails();
        //modelMap.addAttribute("userDetails", user.getUserDetails());
        context.setVariable("userDetails", userDetails);

        if (userDetails !=null) {
            if (userDetails.getImage() != null &&
                    (userDetails.getImage().getSize() != null && userDetails.getImage().getSize() != 0)) {
                //modelMap.addAttribute("imageId", userDetails.getImage().getImageId());
                context.setVariable("imgId", userDetails.getImage().getImageId());
            }
            //modelMap.addAttribute("dateBirth", userDetails.getDateBirth());
            context.setVariable("dateBirthS", userDetails.getDateBirth());
        }


        //context.setVariables(modelMap);

//        // Создаем контекст, реализующий IWebContext
//        ServletRequestAttributes servletRequestAttributes =
//                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = servletRequestAttributes.getRequest();
//
//        // Создаем IWebContext/ IWebContext:
//        // Интерфейс IWebContext  предоставляет доступ к информации
//        // о контексте веб-приложения, такой как getContextPath(),
//        // getServletPath(),  getRequestURI() и т.д.
//        IWebContext webContext = new Context(context) {
//            @Override
//            public String getContextPath() {
//                return request.getContextPath();
//            }
//
//            @Override
//            public String getServletPath() {
//                return request.getServletPath();
//            }
//
//        };





        // Рендеринг шаблона с помощью Thymeleaf
        return templateEngine.process("profileForm", context);
    }

}
