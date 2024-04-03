package com.RoCo.entities.Account;


import com.RoCo.entities.CatalogEnt.BucketEnt;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
//org.jetbrains.annotations.Nullable;

@Data
@AllArgsConstructor
//@NoArgsConstructor
@Entity
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
    private boolean archive;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(cascade = CascadeType.REMOVE)
    @Nullable
    private BucketEnt bucket;

    public User() {
    }

    public User(String name, String password, String email, UserRole role) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
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

    public boolean isArchive() {
        return archive;
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

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setBucket(@Nullable BucketEnt bucket) {
        this.bucket = bucket;
    }
}
