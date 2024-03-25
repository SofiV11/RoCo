//package com.RoCo.entities.Account;
//
//import jakarta.persistence.*;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Set;
//
//@Entity
//@Table(name="users_role")
//public class Role implements GrantedAuthority {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long roleId;
//    private String roleName;
//    @Transient
//    @ManyToMany(mappedBy = "roles")
//    private Set<SiteUser> users;
//
//    public Role(Long roleId, String roleName, Set<SiteUser> users) {
//        this.roleId = roleId;
//        this.roleName = roleName;
//        this.users = users;
//    }
//
//    public Role() {
//    }
//
//    public Role(Long roleId, String roleName) {
//        this.roleId = roleId;
//        this.roleName = roleName;
//    }
//
//    public Role(Long roleId) {
//        this.roleId = roleId;
//    }
//
//    public Long getRoleId() {
//        return roleId;
//    }
//
//    public void setRoleId(Long roleId) {
//        this.roleId = roleId;
//    }
//
//    public String getRoleName() {
//        return roleName;
//    }
//
//    public void setRoleName(String roleName) {
//        this.roleName = roleName;
//    }
//
//    public Set<SiteUser> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<SiteUser> users) {
//        this.users = users;
//    }
//
//    @Override
//    public String getAuthority() {
//        return getRoleName();
//    }
//}
