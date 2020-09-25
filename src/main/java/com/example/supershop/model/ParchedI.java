package com.example.supershop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","fieldHandler"})
public class ParchedI extends BaseModel implements LineItemPrice, Serializable {
    private static final long SerialisationUUID = 1454L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties(value = {"category", "stockList", "vat", "details", "expireDate"})
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Product product;
    private int productQuantity;
    private  double totalItemPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    @JsonIgnoreProperties(value = {"invoiceItems"})
    private SalesInvoice invoice ;

    public ParchedI(Product product, int productQuantity) {
        this.product = product;
        this.productQuantity = productQuantity;
        this.totalItemPrice = this.getCalculatePrice();
    }

    @Override
    @JsonIgnore
    public Double getCalculatePrice() {
        var sellPrice = this.product.getPrice().getSellPrice();
        double vat = (product.getVat()/100)*this.productQuantity;
        return  (this.productQuantity * sellPrice) + vat;
    }
    public Double  getTotalVat(){
      return   product.getVat()*this.productQuantity;
    }
}
