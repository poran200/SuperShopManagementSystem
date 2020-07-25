package com.example.supershop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WareHouse extends BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long warehouseId;
    private String  name;
    @ManyToMany(cascade = {MERGE, PERSIST})
    @JsonIgnoreProperties(value = {"product"})
    private Set<Product> products;
    @OneToOne( fetch = FetchType.LAZY)
    private  Address address;
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"employee"})
    private User manager;
    public void  addProduct(Product product){
        if (products == null){
            this.products = new HashSet<>();
        }
        products.add(product);
    }

    @Override
    public String toString() {
        return "WareHouse{" +
                "warehouseId=" + warehouseId +
                ", name=" + name +
                '}';
    }
}
