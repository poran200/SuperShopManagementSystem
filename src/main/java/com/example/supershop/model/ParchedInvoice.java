package com.example.supershop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class ParchedInvoice extends Invoice  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY, cascade = ALL)
    @JsonIgnoreProperties({"invoice"})
    private List<ParchedInvoiceLineItem> invoiceItems;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {PERSIST, MERGE,
            DETACH, REFRESH}, optional = false)
    @JoinColumn
    @JsonIgnore
    private User user;
    @ManyToOne(
            fetch = FetchType.LAZY,cascade = {PERSIST, MERGE,
            DETACH, REFRESH},
            optional = false)
    @JoinColumn
    @JsonIgnore
    private Provider provider;
    @ManyToOne(
            fetch = FetchType.LAZY,cascade = {PERSIST, MERGE,
            DETACH, REFRESH},
            optional = false)
     private WareHouse wareHouse;

    public void add(ParchedInvoiceLineItem lineItem){
        if (invoiceItems == null) {
            invoiceItems = new ArrayList<>();
        }
        lineItem.setInvoice(this);
        invoiceItems.add(lineItem);
    }

    public void remove(ParchedInvoiceLineItem lineItem) {
        lineItem.setInvoice(null);
        this.invoiceItems.remove(lineItem);
    }

    public void addAll(List<ParchedInvoiceLineItem> lineItems) {
        if (invoiceItems == null) {
            invoiceItems = new ArrayList<>();
        }
        lineItems.forEach(lineItem -> {
            lineItem.setInvoice(this);
            invoiceItems.add(lineItem);
        });
    }
}
