package com.RoCo.entities.Account;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.annotation.Nullable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "profile_images")
public class ProfileImageEnt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imageId;

    private String name;

    private String originalFileNAme;

    private Long size;

    private String contentType;

   // @Setter
    //@Lob
    private byte[] bytes;

    @OneToOne
    @JoinColumn(name="user_details_id")
    @JsonBackReference
    private UserDetailsEnt userDetails;

    public void setName(String name) {
        this.name = name;
    }

    public void setOriginalFileNAme(String originalFileNAme) {
        this.originalFileNAme = originalFileNAme;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setUserDetails(UserDetailsEnt userDetails) {
        this.userDetails = userDetails;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Long getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public String getOriginalFileNAme() {
        return originalFileNAme;
    }

    public Long getSize() {
        return size;
    }

    public String getContentType() {
        return contentType;
    }

    @Column(length = 16000000)
    @Basic(fetch = FetchType.LAZY)
    public byte[] getBytes() {
        return bytes;
    }

    public UserDetailsEnt getUserDetails() {
        return userDetails;
    }
}
