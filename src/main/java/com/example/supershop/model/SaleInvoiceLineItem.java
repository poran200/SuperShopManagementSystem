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
@Table(name = "Sale_invoice_line_item")
public class SaleInvoiceLineItem extends BaseModel implements LineItemPrice, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn
    @JsonIgnoreProperties({"category"})
    private Product product;
    private int itemQuantity;
    private double totalItemLinePrice;

    public SaleInvoiceLineItem(Product product, int parchedItemQuantity) {
        this.product = product;
        this.itemQuantity = parchedItemQuantity;
        this.totalItemLinePrice = getCalculatePrice();
    }

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn
    @JsonIgnore
    private SalesInvoice invoice;

    @Override
    @JsonIgnore
    public Double getCalculatePrice() {
        var sellPrice = this.product.getPrice().getSellPrice();
        double totalVat = ((product.getVat() / 100) * sellPrice) * this.itemQuantity;
        return (this.itemQuantity * sellPrice) + totalVat;
    }

    @JsonIgnore
    public Double getTotalVat() {
        return ((product.getVat() / 100) * this.product.getPrice().getSellPrice()) * this.itemQuantity;
    }
}
