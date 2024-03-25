//package com.RoCo.repositories.AccountRepo;
//
//import com.RoCo.entities.Account.SiteUser;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//public interface SiteUserRepo extends JpaRepository<SiteUser, Long> {
//    SiteUser findByUserName(String userName);
//
//    SiteUser findByUserNameAndPass(String username, String pass);
//
//    @Query(value = "SELECT nextval(pg_get_serial_sequence('site_users', 'id'))", nativeQuery = true)
//    Long getNextId();
//}
