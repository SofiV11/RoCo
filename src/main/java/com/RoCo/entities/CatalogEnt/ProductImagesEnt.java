package com.RoCo.entities.CatalogEnt;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "product_images")
public class ProductImagesEnt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imageId;

    private String name;

    private String originalFileNAme;

    private Long size;

    private String contentType;

    private boolean isPreviewImage;

    @Lob
    private byte[] bytes;

//    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER) // get image and all connected objects
//    @JoinColumn(name="product_id")
//    private ProductEnt product;
}