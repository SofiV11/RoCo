package com.RoCo.entities.CatalogEnt;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name =  "order_details")
public class OrderDetailsEnt {

    private static final String SEQ_NAME = "order_details_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name=SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEnt order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEnt product;

    private Double amount;

    private Double price;


}
