package com.example.supershop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Stock extends BaseModel implements Serializable {
    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long stockId;
    private  int quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "category")
    private Product product;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"productList", "shopAddress", "employee"})
    private Shop shop;

    public Stock(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockId=" + stockId +
                ", quantity=" + quantity +
                ", product=" + product.getProductId() +
                ", shop=" + shop.getShopId() +
                '}';
    }
}
