package com.RoCo.entities.CatalogEnt;


//import com.RoCo.entities.Account.SiteUser;
import com.RoCo.entities.Account.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name =  "orders")
public class OrderEnt {

    private static final String SEQ_NAME = "order_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name=SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long orderId;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    //private SiteUser user;
    private User user;

    private Double sum;

    private String adress;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderDetailsEnt> details;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}
