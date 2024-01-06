package com.RoCo.controllers;

import com.RoCo.entities.Account.SiteUser;
import com.RoCo.entities.NewsEnt.PostRec;
import com.RoCo.repositories.AccountRepo.SiteUserRepo;
import com.RoCo.services.AccountServ.SiteUserServ;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class AccountController {

    @Autowired
    private SiteUserRepo userRepo;
    @Autowired
    private SiteUserServ userServ;

//    @GetMapping("/Account") // login and registration for unknown, account page for auth user
//    public String LoginPage(Model model){
//        return "Account/loginPage.html";
//    }

    @GetMapping("/MyAccount") // login and registration for unknown, account page for auth user
    public String MyAccount(Model model){
        return "Account/myAcc.html";
    }

    @GetMapping("/Registration") // login and registration for unknown, account page for auth user
    public String RegPage(Model model){
        model.addAttribute("userForm", new SiteUser());
        return "Account/registrationPage.html";
    }

    @PostMapping("/Registration")
    public String regUser(@ModelAttribute("userForm") @Valid SiteUser userForm,
                          BindingResult bindingResult,
                          Model model) {

        if (bindingResult.hasErrors()) {
            return "Registration";
        }
        if (!userForm.getPassword().equals(userForm.getPassConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "Registration";
        }
        if (!userServ.saveSiteUser(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "Registration";
        }

//        model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
//        model.addAttribute("passwordError", "Пароли не совпадают");
        return "redirect:/MyAccount";
    }


    @PostMapping("/Account")
    public String getLogPass(@RequestParam  String login,
                             @RequestParam  String pass,
                             BindingResult bindingResult,
                      //@RequestParam(value="imgUrl", required = false)  String imgUrl,
                      Model model) {
        if (bindingResult.hasErrors()) {
            return "/Account";
        }
        return "redirect:/MyAccount";

    }

    @GetMapping("/Account")
    public String getAcc(Model model) {

        return "Account/loginPage.html";
    }


}
//@PostMapping("/Blog/addPost")
//public String GetNewPostData( @RequestParam  String postTitle,
//                              @RequestParam  String brief,
//                              @RequestParam  String fullText,
//                              //@RequestParam  Date publDate,
//                              @RequestParam  String author,
//                              @RequestParam(value="imgUrl", required = false)  String imgUrl,
//                              Model model){
//    //Date dateP = new Date(publDate);
////        try {
////            SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("dd.MM.yyyy");
////            simpleDateFormat.parse(publDate);
////        }catch (Exception e){
////            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
////        }
//    Date publDate = new Date(System.currentTimeMillis());
//
//    if (imgUrl == null || imgUrl.isEmpty() ) {
//        PostRec publication = new PostRec(postTitle, brief, fullText, publDate, author);
//        postRepo.save(publication);
//    }
//    else {
//        PostRec publication = new PostRec(postTitle, brief, fullText, publDate, author, imgUrl);
//        postRepo.save(publication);
//    }
//    //postRepo.save(publication);
//    Iterable<PostRec> posts = postRepo.findAll();
//    model.addAttribute("posts", posts);
//    return "redirect:/Blog";
//}