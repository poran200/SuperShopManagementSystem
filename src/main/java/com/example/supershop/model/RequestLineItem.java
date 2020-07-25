package com.example.supershop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
@EqualsAndHashCode( of = {"id"},callSuper = false)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestLineItem extends BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private Product product;
    private  int quantity;
    @ManyToOne
    private ProductRequest productRequest;

    @Override
    public String toString() {
        return "RequestLineItem{" +
                "id=" + id +
                ", product=" + product.getProductName() +
                ", quantity=" + quantity +
                '}';
    }
}
