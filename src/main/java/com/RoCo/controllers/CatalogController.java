package com.RoCo.controllers;


import com.RoCo.entities.CatalogEnt.ProductCatEnt;
import com.RoCo.entities.CatalogEnt.ProductEnt;
import com.RoCo.entities.NewsEnt.PostRec;
import com.RoCo.mappers.ProductToDtoMapper;
import com.RoCo.models.BucketDto;
import com.RoCo.models.Product;
import com.RoCo.services.CatalogServ.BucketServ;
import com.RoCo.services.CatalogServ.ProductServ;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

@Controller
//@RequestMapping("/catalog")
//@RequiredArgsConstructor
public class CatalogController {
    @Autowired
    private ProductServ productServ;

    @Autowired
    private BucketServ bucketServ;




//    private final ProductToDtoMapper mapper;
//
//    public CatalogController(ProductServ productServ, ProductToDtoMapper mapper) {
//        this.productServ = productServ;
//        this.mapper = mapper;
//    }
    @Value("${upload.path}") // получение значения для переменной
    private String uploadPath;
    @GetMapping("/Catalog")
    public String CatalogPage(Model model){
        return CatalogPagePaginatedSorted(model, "name", "asc", 1);//"redirect:Catalog/page/1";
    }

    @GetMapping("/Catalog/detail{id}")
    public String ProductDetail(@PathVariable Long id, Model model){
        Product product = productServ.getProductById(id);
        model.addAttribute("product", product);
        return "CatalogPage/productDetail.html";
    }
    @GetMapping("/Catalog/page/{pageNo}")
    public String CatalogPagePaginatedSorted(Model model,
                                             @RequestParam("sortField") String sortField,
                                             @RequestParam("sortDir") String sortDir,
                              //@RequestParam(value = "sortField", defaultValue = "name") String sortField,
                              //@RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                              @PathVariable(value = "pageNo", required = false) Integer pageNo
                              //@PageableDefault(sort = {"pk"}, direction = Direction.DESC) Pageable pageable
                              ){
        if(pageNo == null){
            List<Product> products= productServ.getAllProducts();
            model.addAttribute("products", products);
        } else {
            int pageSize = 8;
            Page<Product> pageProducts= productServ.findPaginated(pageNo, pageSize, sortField, sortDir);
            List<Product> productList = pageProducts.getContent();
            model.addAttribute("totalItems", pageProducts.getTotalElements());
            model.addAttribute("products", productList);
            model.addAttribute("totalPages", pageProducts.getTotalPages());
        }
        List<ProductCatEnt> categories = productServ.getAllCategories(); //List<String> getAllCategories()
        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", pageNo);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("catlabel", null);
        return "CatalogPage/productList.html";
    }

//    @GetMapping("/Catalog")
//    public String getProducts(Model model,
//                              @RequestParam(defaultValue = "1") int page,
//                              @RequestParam(defaultValue = "id,asc") String[] sort,
//                              @RequestParam(required = false) String keyword,)
//
//    {
//        String sortField = sort[0];
//        String sortDirection = sort[1];
//
//        Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
//        Order order = new Order(direction, sortField);
//        Pageable pageable = PageRequest.of(page - 1, 20, Sort.by(order));
//        Page<Product> pages;
//        if (keyword == null) {
//            pages = productServ.getAllProducts();
//        } else {
//            pages = productServ.findByTitleContainingIgnoreCase(keyword, pageable);
//            model.addAttribute("keyword", keyword);
//        }
//        List<Product> products= productServ.getAllProducts();
//        List<ProductCatEnt> categories= productServ.getAllCategories(); //List<String> getAllCategories()
//        model.addAttribute("products", products);
//        model.addAttribute("categories", categories);
//        model.addAttribute("catlabel", null);
//        return "CatalogPage/productList.html";
//    }

    @GetMapping("/Catalog/category{cat_id}") //TODO add pagination
    public String CatalogPAgeCateg( @PathVariable Long cat_id, Model model){
        List<Product> products = productServ.findByCategory(cat_id);
        List<ProductCatEnt> categories= productServ.getAllCategories(); //List<String> getAllCategories()
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("catlabel", productServ.getCatEntById(cat_id).getLabel());
        return "CatalogPage/productList.html";
    }


    @GetMapping("/Catalog/add")
    public String AddNewProduct(Model model){
        List<ProductCatEnt> categories= productServ.getAllCategories();
        model.addAttribute("categories", categories);
        return "CatalogPage/addProduct.html";
    }


    @PostMapping("/Catalog/add")
    public String AddNewProduct( @RequestParam  String name,
                                 @RequestParam  Long category,
                                 @RequestParam  Double price,
                                 @RequestParam  Boolean isAvailable,
                                 @RequestParam  String descr,
                                 @RequestParam(value="img", required = false) MultipartFile img,
                                 Model model) throws IOException {
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

                productServ.addProduct(new Product(null, name, productServ.getCatEntById(category).getLabel(), price, ("images/" + imgName), isAvailable, descr));

            } catch (IOException e) {
                //throw new RuntimeException(e);
                productServ.addProduct(new Product(null, name, productServ.getCatEntById(category).getLabel(), price, null, isAvailable, descr));
            }
        }

        List<Product> products = productServ.getAllProducts();
        model.addAttribute("products", products);

//        List<ProductCatEnt> categories= productServ.getAllCategories();
//        model.addAttribute("categories", categories);

        return "redirect:/Catalog"; // or html
    }


    @GetMapping("/bucket_{id}")
    public String addBucket(@PathVariable Long id, Principal principal, Model model){
        if(principal == null){
            return "redirect:/Catalog";
        }

        productServ.addToUserBucket(id, principal.getName());

        //model.addAttribute("bucketCount", bucketServ.getCountProductsInBucket(principal));
        return "redirect:/Catalog/detail%s".formatted(id);
    }

    @GetMapping("/Bucket")
    public String showBucket(Model model, Principal principal){
        if(principal==null){
            model.addAttribute("bucket", new BucketDto());
        } else {
            BucketDto bucketDto = bucketServ.getBucketByUSer(principal.getName());
            model.addAttribute("bucket", bucketDto);
        }
        return "CatalogPage/bucket.html";
    }



}
