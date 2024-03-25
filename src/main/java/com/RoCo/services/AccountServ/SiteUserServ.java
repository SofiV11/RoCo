//package com.RoCo.services.AccountServ;
//
//import com.RoCo.entities.Account.Role;
//import com.RoCo.entities.Account.SiteUser;
//import com.RoCo.entities.Account.UserRole;
//import com.RoCo.models.SiteUserDto;
//import com.RoCo.repositories.AccountRepo.SiteUserRepo;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//
//@Service
//public class SiteUserServ implements UserDetailsService {
//
//    @PersistenceContext
//    private EntityManager em;
//    @Autowired
//    SiteUserRepo userRepo;
//    @Autowired
//    SiteUserRepo roleRepo;
//    @Autowired
//    BCryptPasswordEncoder bPassEncoder; ///////////////
//
//    @Autowired
//    private final PasswordEncoder encoder;
//
//    public SiteUserServ(PasswordEncoder encoder) {
//        this.encoder = encoder;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        SiteUser user = userRepo.findByUserName(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("");
//        } else {return user;}
////
//    }
//
//    public SiteUser findSiteUserById(Long id){
//        Optional<SiteUser> user = userRepo.findById(id);
//        return user.orElse(new SiteUser());
//    }
//
//    public List<SiteUser> findAllSiteUsers(){
//        return userRepo.findAll();
//    }
//
//    public boolean saveSiteUser( SiteUser user){
//        SiteUser userDb = userRepo.findByUserName(user.getUserName());
//        if(userDb != null)
//            return false;
//        else {
//            userDb = new SiteUser();
//            userDb.setUserName(user.getUserName());
//            userDb.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
//            //userDb.setPass(bPassEncoder.encode(user.getPassword()));
//            userDb.setPass(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(4)));
//            userRepo.save(userDb);
//            return true;
//        }
//    }
//
//
//    //@Override
//
//
//    public boolean delSiteUser( SiteUser user){
//        if (userRepo.findById(user.getId()).isPresent()){
//            userRepo.deleteById(user.getId());
//            return true;
//        }
//        return false;
//
//    }
//
//    public List<SiteUser> siteUserGtList(Long idMin){
//        return em.createQuery("SELECT u.* FROM site_users WHERE u.id > :paramId", SiteUser.class)
//                .setParameter("paramId", idMin).getResultList();
//    }
//
//
//    public Boolean siteUserCheck(String log, String pass){
//        //String dbPass = bPassEncoder.encode(pass);
//        //String dbPass = BCrypt.hashpw(pass, BCrypt.gensalt(4));
////        String res = em.createQuery("SELECT u.* FROM site_users u" +
////                                  "WHERE u.user_name = :log AND u.pass = :dbPass", SiteUser.class)
////                .setParameter("log", log).setParameter("dbPass", dbPass).toString();
//        //if(userRepo.findByUserName(log).getPass())
//        //UserDetails ud = userRepo.findByUserName(log);
//        //if(userRepo.findByUserNameAndPass(log, dbPass) != null) return true;
//        //else return false;
//
//        return BCrypt.checkpw(pass.toString(), userRepo.findByUserName(log).getPass());
//    }
//
//
////    public List<SiteUser> siteUsersByRole(String role){
////        return em.createQuery("SELECT u.* FROM site_users u" +
////                                 "LEFT JOIN users_role r ON r.role_id = u.roles" +
////                                  "WHERE r.role_name = :pRole", SiteUser.class)
////                .setParameter("pRole", role).getResultList();
////    }
//
////        public boolean CheckLogPass(String login, String password){
////        return em.createQuery("SELECT u.* " +
////                                 "FROM site_users u" +
////                                  "WHERE r.login = :pLog AND ", SiteUser.class)
////                .setParameter("pLog", login).setParameter( "pPass", password);
////    }
//
//
//}
