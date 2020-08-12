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

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductRequest extends BaseModel implements Serializable {
     private static  final Long SRILALVERSIONUUID= 145757L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long  requestId;
    @JsonIgnoreProperties({"productRequest"})
    @OneToMany(mappedBy = "productRequest", fetch = FetchType.LAZY, cascade = {PERSIST, MERGE, DETACH, REFRESH})
     private List<RequestLineItem> requestLineItems;
    private boolean isAccept;
    private String status;
    private int totalItem;
    @OneToOne(cascade = ALL)
    @JsonIgnore
    private Shop requestShop;
    @OneToOne(cascade = ALL)
    @JsonIgnore
    private WareHouse wareHouse;
    public void addItem(List<RequestLineItem> lineItem){
        if (requestLineItems == null){
            requestLineItems = new ArrayList<>();
        }
        lineItem.forEach(lineItem1 -> lineItem1.setProductRequest(this));
        requestLineItems.addAll(lineItem);
    }

    @Override
    public String toString() {
        return "ProductRequest{" +
                "requestId=" + requestId +
                ", isAccept=" + isAccept +
                ", status='" + status + '\'' +
                ", totalItem=" + totalItem +
                '}';
    }
}
