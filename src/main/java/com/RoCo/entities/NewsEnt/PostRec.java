package com.RoCo.entities.NewsEnt;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.annotation.Nullable;
import java.util.Date;

@Entity
@Table(name = "posts")
@Data //getters и setters
public class PostRec {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    private String postTitle;

    private String brief;

//    @Column(name="full_text", nullable=false, length=4000)

    private String fullText;

    @DateTimeFormat(pattern = "dd.mm.yyyy")
    @Temporal(TemporalType.DATE)
    private Date publDate;

    private String author;

    @Nullable
    private String imgUrl;

    public void setPk(Long id) {
        this.pk = id;
    }

    public PostRec( String postTitle, String brief, String fullText, Date publDate, String author, String imgUrl) {
        this.postTitle = postTitle;
        this.brief = brief;
        this.fullText = fullText;
        this.publDate = publDate;
        this.author = author;
        this.imgUrl = imgUrl;
    }
    public PostRec( String postTitle, String brief, String fullText, Date publDate, String author) {
        this.postTitle = postTitle;
        this.brief = brief;
        this.fullText = fullText;
        this.publDate = publDate;
        this.author = author;
    }

    public PostRec() {
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public void setPublDate(Date publDate) {
        this.publDate = publDate;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Long getPk() {
        return pk;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getBrief() {
        return brief;
    }

    public String getFullText() {
        return fullText;
    }

    public Date getPublDate() {
        return publDate;
    }

    public String getAuthor() {
        return author;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
