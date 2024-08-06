package com.RoCo.controllers;


import com.RoCo.entities.Account.User;
import com.RoCo.entities.Account.UserDetailsEnt;
import com.RoCo.services.AccountServ.MailSender;
import com.RoCo.entities.Account.UserRole;
import com.RoCo.models.SiteUserDto;
import com.RoCo.repositories.AccountRepo.UserRepo;
import com.RoCo.services.AccountServ.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Controller
public class UserController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userServ;
    @Autowired
    private MailSender mailSender;

    @GetMapping("/Account")
    public String getLogin(Model model) {
        //model.addAttribute("isAuth", userServ.findLoggedInUsername());
//        if(SecurityContextHolder.getContext().getAuthentication().getName()!=null
//                || !SecurityContextHolder.getContext().getAuthentication().getName().isEmpty()){
        String authRole = userServ.getAuth();
        if(authRole.equals(UserRole.ADMIN.name())){
                model.addAttribute("usersList", userServ.getAllUsersForAdmin());
                return getUserDetails(model);
            } else if (authRole.equals(UserRole.CLIENT.name())) return getUserDetails(model);
            else return "Account/loginPage.html";

//        }
//        return "Account/loginPage.html";
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
                          @RequestParam(value="img", required = false) MultipartFile img,
                          //BindingResult bindingResult,
                          // @ModelAttribute("user") @Valid SiteUserDto user
                          Model model) {

        User savedUser = userServ.saveAndGet(user, img);
        if(savedUser != null){
            if (savedUser.getActivated()) return "redirect:/Account";
            model.addAttribute("activationMessage", "You're not activated. Please, see email!");
            return "redirect:/Account";
        } else {
            //model.addAttribute("user", user);
            model.addAttribute("errorText", userServ.getErrorBuffer() != null ? userServ.getErrorBuffer() : "Unknown error");
            return "Account/registrationPage.html";
        }

    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActivated = userServ.activateUser(code);
        if(isActivated) model.addAttribute("message", "Account successfully activated!");
        else model.addAttribute("message", "Activation code not found!");
        return "redirect:/Account";
    }


    @RequestMapping(value="/Account/edit", method = RequestMethod.POST)
    public String editUserDetails(
                           @ModelAttribute("hyeta") UserDetailsEnt userDetailsEnt,
                           @ModelAttribute("user") User user,
                           @RequestParam(value="profileImage", required = false) MultipartFile img,
/*                           @RequestParam(value="fio",required = false) String fio,
                           @RequestParam(value = "city", required = false) String city,
                           @RequestParam(value="phone", required = false) String phone,*/
                           //@RequestParam(value="dateBirth", required = false) Date dateBirth,
                           @RequestParam(value="dateBirthS", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date,
                           Model model){
        User currentUser = userRepo.findFirstByName(SecurityContextHolder.getContext().getAuthentication().getName());
        UserDetailsEnt previousUserDetails = currentUser.getUserDetails();//userServ.getUserDetailsByUser(currentUser);
 /*       if(previousUserDetails == null){
            //userServ.createUserDetails(user);
            //previousUserDetails = user.getUserDetails();
            previousUserDetails = new UserDetailsEnt();
        }*/

/*        UserDetailsEnt userDetailsEntNew = new UserDetailsEnt();
        userDetailsEntNew.setFio(fio);
        userDetailsEntNew.setCity(city);
        userDetailsEntNew.setPhone(phone);
        userDetailsEntNew.setDateBirth(new Date(dateBirth));*/




        //currentUser.setEmail(user.getEmail());
        //boolean isUserEdited = userServ.editUser(currentUser);
        //if(!isUserEdited) model.addAttribute("error", userServ.getErrorBuffer());

        currentUser.setEmail(user.getEmail());
        currentUser.setName(user.getName());


        if(previousUserDetails != null && previousUserDetails.getId()!=null) {
            userDetailsEnt.setId(previousUserDetails.getId());
            userDetailsEnt.setImage(previousUserDetails.getImage());
        }
        userDetailsEnt.setUser(currentUser);
        userDetailsEnt.setDateBirth(date);
        if (userServ.editUserDetails(userDetailsEnt, img)) {
            model.addAttribute("userDetails", userServ.getUserDetailsByUser(currentUser));
        } else model.addAttribute("error", userServ.getErrorBuffer());


/*        ProfileImageEnt imgEnt = userServ.toImageEntity(img);
        userDetailsEnt.setImage(imgEnt);
        userDetailsEnt.setDateBirth(date);
        userDetailsEnt.setUser(currentUser);
        currentUser.setUserDetails(userDetailsEnt);
        try {
            userServ.save(currentUser);
            model.addAttribute("userDetails", userServ.getUserDetailsByUser(currentUser));
        }catch (Exception e){
            model.addAttribute("error", userServ.getErrorBuffer());
        }*/

        return "redirect:/Account";
    }

    @RequestMapping(value="/Account/A_edit", method = RequestMethod.POST)
    public String editUserDetailsFromAdmin(
            @ModelAttribute("hyeta") UserDetailsEnt userDetailsEnt,
            @ModelAttribute("user") User user,
            @RequestParam(value="profileImage", required = false) MultipartFile img,
            @RequestParam(value="dateBirthS", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date,
            Model model){
        User currentUser = userServ.findUserById(user.getUserId());
        UserDetailsEnt previousUserDetails = currentUser.getUserDetails();//userServ.getUserDetailsByUser(currentUser);

        currentUser.setEmail(user.getEmail());
        currentUser.setName(user.getName());

        if(previousUserDetails != null && previousUserDetails.getId()!=null) {
            userDetailsEnt.setId(previousUserDetails.getId());
            userDetailsEnt.setImage(previousUserDetails.getImage());
        }
        userDetailsEnt.setUser(currentUser);
        userDetailsEnt.setDateBirth(date);
        user.setUserDetails(userDetailsEnt);
        if (userServ.editUserDetails(userDetailsEnt, img)) {
            model.addAttribute("userDetails", userServ.getUserDetailsByUser(currentUser));
        } else model.addAttribute("error", userServ.getErrorBuffer());

        return "redirect:/Account";
    }
    @RequestMapping(value="/Account/edit", method = RequestMethod.GET)
    public String getUserDetails(Model model){
        model.addAttribute("selectedSection", null);
        User currentUser = userRepo.findFirstByName(SecurityContextHolder.getContext().getAuthentication().getName());
        //UserDetailsEnt previousUserDetails = userServ.getUserDetailsByUser(currentUser);
        UserDetailsEnt previousUserDetails = currentUser.getUserDetails();
        model.addAttribute("user", currentUser);
        if(previousUserDetails == null) {
            model.addAttribute("error", "details not found");
            model.addAttribute("hyeta", new UserDetailsEnt());
            model.addAttribute("imgId", null);

        }
        else {
            model.addAttribute("hyeta", previousUserDetails);
            model.addAttribute("dateBirthS", previousUserDetails.getDateBirth());
            if(previousUserDetails.getImage()!=null &&
                    (previousUserDetails.getImage().getSize()!=null && previousUserDetails.getImage().getSize()!=0)) {
                model.addAttribute("imgId", previousUserDetails.getImage().getImageId());
            }
        }
        return "Account/myAcc.html";
    }

    @RequestMapping(value="/Account/myOrders", method = RequestMethod.GET)
    public String getMyOrders(Model model){
        model.addAttribute("selectedSection", "myOrders");
        return "Account/myAcc.html";
    }

    @RequestMapping(value="/Account/myPosts", method = RequestMethod.GET)
    public String getMyPosts(Model model){
        model.addAttribute("selectedSection", "myPosts");
        return "Account/myAcc.html";
    }


//    @RequestMapping(value="/Registration", method = RequestMethod.POST)
//    public String regUser(@ModelAttribute("user") @Valid SiteUserDto user,
//                          //BindingResult bindingResult,
//                          // @ModelAttribute("user") @Valid SiteUserDto user
//                          Model model) {
//
//        if(userServ.save(user)){
//            return "redirect:/Account";
//        } else {
//            model.addAttribute("user", user);
//            return "redirect:/Registration";
//        }
//
//    }

//    @GetMapping("/ping")
//    public String doSomething(Model model) {
//        String message = "ping";
//        mailSender.sendMail("sona.ivanova.334@mail.ru", "!!!!", message);
//        return "redirect:/Registration";
//    }
}
