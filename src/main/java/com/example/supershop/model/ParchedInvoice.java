package com.example.supershop.model;

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
@EqualsAndHashCode(of = "Id", callSuper = false)
public class ParchedInvoice extends Invoice  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long  Id;
    @OneToMany(mappedBy = "invoice" ,fetch = FetchType.LAZY ,cascade = ALL)
    private List<ParchedInvoiceLineItem> invoiceItems;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {PERSIST, MERGE,
            DETACH, REFRESH}, optional = false)
    @JoinColumn
    private User user;
    @ManyToOne(
            fetch = FetchType.LAZY,cascade = {PERSIST, MERGE,
            DETACH, REFRESH},
            optional = false)
    @JoinColumn
    private Provider provider;
    @ManyToOne(
            fetch = FetchType.LAZY,cascade = {PERSIST, MERGE,
            DETACH, REFRESH},
            optional = false)
     private WareHouse wareHouse;

    public void add(ParchedInvoiceLineItem lineItem){
         if (invoiceItems== null){
             invoiceItems = new ArrayList<>();
         }
         invoiceItems.add(lineItem);
    }
    public void remove(ParchedInvoiceLineItem lineItem){
        lineItem.setInvoice(null);
        this.invoiceItems.remove(lineItem);
    }
}
