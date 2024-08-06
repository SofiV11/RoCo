package com.RoCo.entities.Account;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_details")
public class UserDetailsEnt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id")
    //private SiteUser user;
    @JsonBackReference
    private User user;

    @Nullable
    private String phone;

    @Nullable
    private String fio;

    @Nullable
    private LocalDate dateBirth;

    @Nullable
    private String city;

    @Nullable
    private String activationCode;

    @Nullable
    @OneToOne(cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private ProfileImageEnt image;

    public void setActivationCode(@Nullable String activationCode) {
        this.activationCode = activationCode;
    }

    public User getUser() {
        return user;
    }

    public UserDetailsEnt(@Nullable String activationCode) {
        this.activationCode = activationCode;
    }
    public UserDetailsEnt(User user) {
        this.setUser(user);
    }

    public UserDetailsEnt() {
    }

    public UserDetailsEnt(User user, @Nullable String activationCode) {
        this.user = user;
        this.activationCode = activationCode;
    }

    public UserDetailsEnt(@Nullable String phone, @Nullable String fio, @Nullable LocalDate dateBirth, @Nullable String city, @Nullable ProfileImageEnt image) {
        this.phone = phone;
        this.fio = fio;
        this.dateBirth = dateBirth;
        this.city = city;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setImage(@Nullable ProfileImageEnt image) {
        this.image = image;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addImageToUser(ProfileImageEnt image){
        image.setUserDetails(this);
        this.setImage(image);
        //images.add(image)
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDetailsEnt that)) return false;
        return Objects.equals(user, that.user) && Objects.equals(phone, that.phone) && Objects.equals(fio, that.fio) && Objects.equals(dateBirth, that.dateBirth) && Objects.equals(city, that.city) && Objects.equals(activationCode, that.activationCode) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, phone, fio, dateBirth, city, activationCode, image);
    }

    public Long getId() {
        return id;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    @Nullable
    public String getFio() {
        return fio;
    }

    @Nullable
    public LocalDate getDateBirth() {
        return dateBirth;
    }

    @Nullable
    public String getCity() {
        return city;
    }

    @Nullable
    public String getActivationCode() {
        return activationCode;
    }

    @Nullable
    public ProfileImageEnt getImage() {
        return image;
    }

    public void setPhone(@Nullable String phone) {
        this.phone = phone;
    }

    public void setFio(@Nullable String fio) {
        this.fio = fio;
    }

    public void setDateBirth(@Nullable LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }

    public void setCity(@Nullable String city) {
        this.city = city;
    }
}
