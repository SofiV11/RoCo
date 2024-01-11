package com.RoCo.controllers;

import com.RoCo.entities.NewsEnt.PostRec;
import com.RoCo.repositories.NewsRepo.PostRecRepo;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;


@Controller
public class NewsController {

    @Autowired
    private PostRecRepo postRepo;

    @Value("${upload.path}") // получение значения для переменной
    private String uploadPath;

    @GetMapping("/Blog")
    public String BlogPage(Model model){
        Iterable<PostRec> posts = postRepo.findAll();
        model.addAttribute("posts", posts);
        return "BlogPage/blogPage.html";
    }

    @GetMapping("/Blog/addPost")
    public String AddPost(Model model){
        return "BlogPage/addNewPost.html";
    }


    //@PostMapping("/Blog/addPost")
    @RequestMapping(value = "/Blog/addPost", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String GetNewPostData( @RequestParam  String postTitle,
                                  @RequestParam  String brief,
                                  @RequestParam  String fullText,
                                //@RequestParam  Date publDate,
                                  @RequestParam  String author,
                                  @RequestParam(value="img", required = false) MultipartFile img,
                                  Model model) throws IOException {
        //Date dateP = new Date(publDate);
//        try {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("dd.MM.yyyy");
//            simpleDateFormat.parse(publDate);
//        }catch (Exception e){
//            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
//        }
        Date publDate = new Date(System.currentTimeMillis());

//        if (imgUrl == null || imgUrl.isEmpty() ) {
//            PostRec publication = new PostRec(postTitle, brief, fullText, publDate, author);
//            postRepo.save(publication);
//        }
//        else {
//            PostRec publication = new PostRec(postTitle, brief, fullText, publDate, author, imgUrl);
//            postRepo.save(publication);
//        }

        if (img != null) {
            try (InputStream is = img.getInputStream()) {
                //System.out.println( "Size:  " + is.available());
                File uploadDir = new File(uploadPath);

                if(!uploadDir.exists()){
                    uploadDir.mkdir();
                }
                //String uuidFile = UUID.randomUUID().toString();
                String imgName = img.getOriginalFilename();

                img.transferTo(new File(uploadPath + "/" + imgName));

                PostRec publication = new PostRec(postTitle, brief, fullText, publDate, author, "images/" + imgName);
                postRepo.save(publication);

            } catch (IOException e) {
                //throw new RuntimeException(e);
                PostRec publication = new PostRec(postTitle, brief, fullText, publDate, author);
                postRepo.save(publication);
            }
        }
        Iterable<PostRec> posts = postRepo.findAll();
        model.addAttribute("posts", posts);
        return "redirect:/Blog";
    }

    @GetMapping("/Blog/post{id}")
    public String BlogDetail(@PathVariable(value="id") long id, Model model){

        if (postRepo.existsById(id)){

//            Optional<PostRec> postrec = postRepo.findById(id);
//            ArrayList<PostRec> data = new ArrayList<>();
//            postrec.ifPresent(data::add);
//            model.addAttribute("post", data);
//
//

            PostRec rec = postRepo.findById(id).orElseThrow();
            model.addAttribute("post", rec);
            Iterable<PostRec> samePosts = postRepo.findAllByAuthor(rec.getAuthor());

            ArrayList<PostRec> sameP = new ArrayList<>();
            samePosts.forEach(sameP::add);
            sameP.removeIf(e -> e.getPk().equals(rec.getPk()));
            model.addAttribute("samePosts", sameP);
            return "BlogPage/postDetail.html";
        }
        else return "BlogPage/blogPage.html";
    }

    @GetMapping("/Blog/post{id}/edit")
    public String PostEdit(@PathVariable(value="id") long id, Model model){

        if (postRepo.existsById(id)){
            Optional<PostRec> postrec = postRepo.findById(id);
            ArrayList<PostRec> data = new ArrayList<>();
            postrec.ifPresent(data::add);
            model.addAttribute("post", data);
            return "BlogPage/editPost.html";
        }
        else return "BlogPage/blogPage.html";
    }

    @PostMapping("/Blog/post{id}/edit")
    public String PostEditReq(@PathVariable(value="id") Long id,
                              @RequestParam  String postTitle,
                              @RequestParam  String brief,
                              @RequestParam  String fullText,
                              //@RequestParam  Date publDate,
                              @RequestParam  String author,
                              @RequestParam(value="img", required = false)  MultipartFile img,
                              Model model){
        PostRec postrec = postRepo.findById(id).orElseThrow();
        postrec.setPostTitle(postTitle);
        postrec.setBrief(brief);
        postrec.setFullText(fullText);
//        postrec.setPublDate();
        postrec.setAuthor(author);

        if (!img.isEmpty()) {
            try (InputStream is = img.getInputStream()) {
                //System.out.println( "Size:  " + is.available());
                File uploadDir = new File(uploadPath);

                if(!uploadDir.exists()){
                    uploadDir.mkdir();
                }
                //String uuidFile = UUID.randomUUID().toString();
                String imgName = img.getOriginalFilename();

                img.transferTo(new File(uploadPath + "/" + imgName));

                postrec.setImgUrl("images/" + imgName);

            } catch (IOException e) {
                //throw new RuntimeException(e);
            }
        }




        postRepo.save(postrec);
        return "redirect:/Blog";
    }

    @PostMapping("/Blog/post{id}/delete")
    public String PostDel(@PathVariable(value="id") long id, Model model) {
        com.RoCo.entities.NewsEnt.PostRec postrec = postRepo.findById(id).orElseThrow();
        postRepo.delete(postrec);
        return "redirect:/Blog";


    }
}
