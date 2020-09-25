package com.example.supershop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.CascadeType.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "productId", callSuper = false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product extends BaseModel implements Serializable {

    private static final long serialVersionUID=1L;
    @Id
    private long productId;
    private String productName;
    private String details;
    private  Date expireDate;
    @Embedded
    private  Price price;
    private  double vat =0.0;
//    @OneToMany(mappedBy = "product",cascade = {PERSIST, MERGE,
//            DETACH, REFRESH})
//    @JsonIgnoreProperties(value = "product")
//    private List<Stock> stockList;
//    public void addStock(Stock stock){
//        if (stockList == null){
//            stockList = new ArrayList<>();
//        }
//        stockList.add(stock);
//    }
//    @OneToMany(mappedBy = "product")
//    private List<WareHouseStock> wareHouseStocks;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {PERSIST, MERGE,
            DETACH, REFRESH}, optional = false)
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties(value = "productList")
    private Category category;
    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Price{
        private  double purchasePrice;
        private  double sellPrice;
    }

}
