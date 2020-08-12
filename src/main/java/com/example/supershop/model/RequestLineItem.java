package com.example.supershop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RequestLineItem extends BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    @JsonIgnoreProperties({"category","price","vat","expireDate"})
    private Product product;
    private  int quantity;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"requestLineItems"})
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
