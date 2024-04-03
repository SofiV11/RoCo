package com.RoCo.controllers;


import com.RoCo.entities.Account.User;
import com.RoCo.models.SiteUserDto;
import com.RoCo.repositories.AccountRepo.UserRepo;
import com.RoCo.services.AccountServ.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userServ;

    @GetMapping("/Account")
    public String getLogin(Model model) {
        //model.addAttribute("isAuth", userServ.findLoggedInUsername());
        return "Account/loginPage.html";
    }

//    @RequestMapping(value="/Account", method = RequestMethod.POST)
//    public String postLogin(//@RequestParam(value="login", required = false)  String login,
//                             //@RequestParam(value="login", required = false)  String pass,
//                             @ModelAttribute("userData") @Valid SiteUserDto userData,
//                             BindingResult bindingResult,
//                             Model model
//                             //@RequestParam(value="imgUrl", required = false)  String imgUrl
//    ) {
//        if (bindingResult.hasErrors()) {
//            return "/Account";
//        }
//        if(!userServ.siteUserCheck(userData.getUserName(), userData.getPass())){
//            model.addAttribute("notFound", "User not found");
//            return "redirect:/Account";
//        }
//        else return "redirect:/MyAccount";
//    }

    @RequestMapping(value="/Registration", method = RequestMethod.GET) // login and registration for unknown, account page for auth user
    public String RegPage(Model model){
        model.addAttribute("user", new SiteUserDto());
        return "Account/registrationPage.html";
    }

    @RequestMapping(value="/Registration", method = RequestMethod.POST)
    public String regUser(@ModelAttribute("user") @Valid SiteUserDto user,
                          //BindingResult bindingResult,
                          // @ModelAttribute("user") @Valid SiteUserDto user
                          Model model) {

        if(userServ.save(user)){
            return "redirect:/Account";
        } else {
            model.addAttribute("user", user);
            return "redirect:/Registration";
        }

    }
}
