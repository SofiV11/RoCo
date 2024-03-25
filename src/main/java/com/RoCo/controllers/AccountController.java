//package com.RoCo.controllers;
//
//import com.RoCo.entities.Account.SiteUser;
//import com.RoCo.repositories.AccountRepo.SiteUserRepo;
//import com.RoCo.services.AccountServ.SiteUserServ;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//public class AccountController {
////    private final AuthenticationManager authenticationManager;
////
////    public AccountController(AuthenticationManager authenticationManager) {
////        this.authenticationManager = authenticationManager;
////    }
//
//    @Autowired
//    private SiteUserRepo userRepo;
//    @Autowired
//    private SiteUserServ userServ;
//
////    @GetMapping("/Account") // login and registration for unknown, account page for auth user
////    public String LoginPage(Model model){
////        return "Account/loginPage.html";
////    }
//
//    @GetMapping("/MyAccount") // login and registration for unknown, account page for auth user
//    public String MyAccount(Model model){
//        return "Account/myAcc.html";
//    }
//
//    @RequestMapping(value="/Registration", method = RequestMethod.GET) // login and registration for unknown, account page for auth user
//    public String RegPage(Model model){
//        model.addAttribute("userForm", new SiteUser());
//        return "Account/registrationPage.html";
//    }
//
//    @GetMapping("/Account")
//    public String getAcc(Model model) {
//        return "Account/loginPage.html";
//    }
//
//
//    @RequestMapping(value="/Registration", method = RequestMethod.POST)
//    public String regUser(@ModelAttribute("userForm") @Valid SiteUser userForm,
//                          BindingResult bindingResult,
//                          Model model) {
//
//        if (bindingResult.hasErrors()) {
//            return "Account/registrationPage.html";
//        }
//        if (!userForm.getPassword().equals(userForm.getPassConfirm())){
//            model.addAttribute("passwordError", "Пароли не совпадают");
//            return "Account/registrationPage.html";
//        }
//        if (!userServ.saveSiteUser(userForm)){
//            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
//            return "Account/registrationPage.html";
//        }
//        return "redirect:/MyAccount";
//    }
//
//
//    @RequestMapping(value="/Account", method = RequestMethod.POST)
//    public String getLogPass(//@RequestParam(value="login", required = false)  String login,
//                             //@RequestParam(value="login", required = false)  String pass,
//                             @ModelAttribute("userData") @Valid SiteUser userData,
//                             BindingResult bindingResult,
//                             Model model
//                      //@RequestParam(value="imgUrl", required = false)  String imgUrl
//                              ) {
//        if (bindingResult.hasErrors()) {
//            return "/Account";
//        }
//        if(!userServ.siteUserCheck(userData.getUserName(), userData.getPass())){
//            model.addAttribute("notFound", "User not found");
//            return "redirect:/Account";
//        }
//        else return "redirect:/MyAccount";
//
//    }
//
////    @PostMapping("/login")
////    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {
////        Authentication authenticationRequest =
////                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
////        Authentication authenticationResponse =
////                this.authenticationManager.authenticate(authenticationRequest);
////        // ...
////        return null;
////    }
//
//
//
//
//
//}
////@PostMapping("/Blog/addPost")
////public String GetNewPostData( @RequestParam  String postTitle,
////                              @RequestParam  String brief,
////                              @RequestParam  String fullText,
////                              //@RequestParam  Date publDate,
////                              @RequestParam  String author,
////                              @RequestParam(value="imgUrl", required = false)  String imgUrl,
////                              Model model){
////    //Date dateP = new Date(publDate);
//////        try {
//////            SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("dd.MM.yyyy");
//////            simpleDateFormat.parse(publDate);
//////        }catch (Exception e){
//////            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
//////        }
////    Date publDate = new Date(System.currentTimeMillis());
////
////    if (imgUrl == null || imgUrl.isEmpty() ) {
////        PostRec publication = new PostRec(postTitle, brief, fullText, publDate, author);
////        postRepo.save(publication);
////    }
////    else {
////        PostRec publication = new PostRec(postTitle, brief, fullText, publDate, author, imgUrl);
////        postRepo.save(publication);
////    }
////    //postRepo.save(publication);
////    Iterable<PostRec> posts = postRepo.findAll();
////    model.addAttribute("posts", posts);
////    return "redirect:/Blog";
////}