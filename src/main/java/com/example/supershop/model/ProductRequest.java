package com.example.supershop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProductRequest extends BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long  requestId;
    @OneToMany(mappedBy = "productRequest", fetch = FetchType.LAZY, cascade = {PERSIST, MERGE, DETACH, REFRESH})
    private List<RequestLineItem> requestLineItems;
    private boolean isAccept;
    private String status;
    private int totalItem;
    @OneToOne
    private Shop requestShop;
    @OneToOne
    private WareHouse wareHouse;

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
