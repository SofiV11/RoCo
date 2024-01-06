package com.RoCo.services.AccountServ;

import com.RoCo.entities.Account.Role;
import com.RoCo.entities.Account.SiteUser;
import com.RoCo.repositories.AccountRepo.SiteUserRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class SiteUserServ implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    SiteUserRepo userRepo;
    @Autowired
    SiteUserRepo roleRepo;
    @Autowired
    BCryptPasswordEncoder bPassEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public SiteUser findSiteUserById(Long id){
        Optional<SiteUser> user = userRepo.findById(id);
        return user.orElse(new SiteUser());
    }

    public List<SiteUser> findAllSiteUsers(){
        return userRepo.findAll();
    }

    public boolean saveSiteUser( SiteUser user){
        SiteUser userDb = userRepo.findByUserName(user.getUserName());
        if(userDb != null) {return false;}
        userDb.setUserName(user.getUserName());
        userDb.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        userDb.setPass(bPassEncoder.encode(user.getPassword()));
        userRepo.save(userDb);
        return true;
    }

    public boolean delSiteUser( SiteUser user){
        if (userRepo.findById(user.getId()).isPresent()){
            userRepo.deleteById(user.getId());
            return true;
        }
        return false;

    }

    public List<SiteUser> siteUserGtList(Long idMin){
        return em.createQuery("SELECT u.* FROM site_users WHERE u.id > :paramId", SiteUser.class)
                .setParameter("paramId", idMin).getResultList();
    }

//    public List<SiteUser> siteUsersByRole(String role){
//        return em.createQuery("SELECT u.* FROM site_users u" +
//                                 "LEFT JOIN users_role r ON r.role_id = u.roles" +
//                                  "WHERE r.role_name = :pRole", SiteUser.class)
//                .setParameter("pRole", role).getResultList();
//    }

//        public boolean CheckLogPass(String login, String password){
//        return em.createQuery("SELECT u.* " +
//                                 "FROM site_users u" +
//                                  "WHERE r.login = :pLog AND ", SiteUser.class)
//                .setParameter("pLog", login).setParameter( "pPass", password);
//    }
}
