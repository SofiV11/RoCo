package com.RoCo.entities.Account;


import com.RoCo.entities.CatalogEnt.BucketEnt;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
//org.jetbrains.annotations.Nullable;

@Data
@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    private static final String SEQ_NAME = "user_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name=SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long userId;

    private String name;

    private String password;

    @Nullable
    private String email;

    @Nullable
    private Boolean activated;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(cascade = CascadeType.REMOVE)
    @Nullable
    @JsonManagedReference
    private BucketEnt bucket;

    @OneToOne(cascade = CascadeType.ALL)
    @Nullable
    @JsonManagedReference
    private UserDetailsEnt userDetails;

    public User() {
    }

    public User(String name, String password, String email, UserRole role) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User(String name, String password, String email, UserRole role, Boolean activated) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.activated = activated;
    }


    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean getActivated() {
        return activated;
    }

    public UserRole getRole() {
        return role;
    }

    public BucketEnt getBucket() {
        return bucket;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setBucket(@Nullable BucketEnt bucket) {
        this.bucket = bucket;
    }

    public void setUserDetails(@Nullable UserDetailsEnt userDetails) {
        this.userDetails = userDetails;
    }

    @Nullable
    public UserDetailsEnt getUserDetails() {
        return userDetails;
    }
}
