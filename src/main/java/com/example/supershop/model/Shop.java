package com.example.supershop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Shop extends BaseModel implements Serializable {
    private static final long serialVersionUID=1L;
    @Id
    private  long shopId;
    private  String shopName;
    @ManyToMany
    @JsonIgnoreProperties(value = "category")
    private  List<Product> productList;

    @OneToOne(fetch = FetchType.LAZY)
    private  Address shopAddress;
    @OneToOne(fetch = FetchType.LAZY)
    private  Employee employee;

    public void  addProduct(Product product){
        if (productList== null){
            productList = new ArrayList<>();
        }
        productList.add(product);
    }

}
