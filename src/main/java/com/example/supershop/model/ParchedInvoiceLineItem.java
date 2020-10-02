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
@Table(name = "parched_invoice_line_item")
public class ParchedInvoiceLineItem extends BaseModel implements LineItemPrice, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int parchedItemId;
    @OneToOne
    @JoinColumn
    @JsonIgnoreProperties({"category"})
    private Product product;
    private int parchedItemQuantity;
    private double totalItemLinePrice;

    public ParchedInvoiceLineItem(Product product, int parchedItemQuantity) {
        this.product = product;
        this.parchedItemQuantity = parchedItemQuantity;
        this.totalItemLinePrice = getCalculatePrice();
    }

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn
    @JsonIgnore
    private ParchedInvoice invoice;

    @Override
    @JsonIgnore
    public Double getCalculatePrice() {
        var sellPrice = this.product.getPrice().getSellPrice();
        double totalVat = ((product.getVat() / 100) * sellPrice) * this.parchedItemQuantity;
        return (this.parchedItemQuantity * sellPrice) + totalVat;
    }

    @JsonIgnore
    public Double getTotalVat() {
        return ((product.getVat() / 100) * this.product.getPrice().getSellPrice()) * this.parchedItemQuantity;
    }
}
