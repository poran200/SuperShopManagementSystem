package com.example.supershop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Entity
//@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "saleInvoiceId", callSuper = false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","fieldHandler"})
public class SalesInvoice extends Invoice implements Serializable {
    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int saleInvoiceId;
    @OneToMany(mappedBy = "invoice", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = {"invoice"})
    private List<ParchedI> invoiceItems;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    @JsonIgnore
    private Shop shop;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;


    public void add(ParchedI lineItem) {
        if (invoiceItems == null) {
            invoiceItems = new ArrayList<>();
        }
        invoiceItems.add(lineItem);
    }

    public void addAll(ParchedI... invoiceLineItems) {
        if (invoiceItems == null) {
            invoiceItems = new ArrayList<>();
        }
        this.invoiceItems.addAll(Arrays.asList(invoiceLineItems));
    }

    public void remove(ParchedI lineItem) {
        this.invoiceItems.remove(lineItem);
    }

//    @Override
//    public double getTotalBill() {
//        return super.getTotalBill()+(invoiceItems.stream().mapToDouble(SaleInvoiceLineItem::getCalculatePrice).sum());
//    }
}
