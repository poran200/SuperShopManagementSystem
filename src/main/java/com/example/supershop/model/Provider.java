package com.example.supershop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"},callSuper = false)
public class Provider  extends BaseModel implements Serializable {
    private static  final  long SerializableVersionUID= 4574L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private  String providerName;
    private  String providerType;
    @ElementCollection
    private List<String> phoneList;
    private  String email;
    private  String description;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE,
            CascadeType.DETACH,CascadeType.REFRESH}, mappedBy = "provider",orphanRemoval = true)
     private  List<ParchedInvoice> invoiceList;

    public void addInvoice(ParchedInvoice invoice ){
        if (invoiceList ==null){
            invoiceList = new ArrayList<>();
        }
        invoiceList.add(invoice);
//        invoice.setProvider(this);
    }
    public void removeInvoice(ParchedInvoice invoice){
//        invoice.setProvider(null);
        this.invoiceList.remove(invoice);
    }

    public void addAll(ParchedInvoice ... invoices){
        if (invoiceList == null){
            invoiceList = new ArrayList<>();
        }
        invoiceList.addAll(Arrays.asList(invoices));
    }
    public Provider(Provider provider) {
        super();
        this.providerName = provider.getProviderName();
        this.providerType= provider.getProviderType();
        this.phoneList= provider.getPhoneList();
        this.email= provider.getEmail();
        this.address= provider.getAddress();

    }
}
