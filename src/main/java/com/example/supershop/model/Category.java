package com.example.supershop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Category extends BaseModel implements Serializable {
     private static final long serialVersionUID=1L;
     @Id
     private long id;
     private String  categoryName;
     @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST,CascadeType.MERGE,
             CascadeType.DETACH,CascadeType.REFRESH},orphanRemoval = true)
     @JsonIgnoreProperties(value = "category")
     private List<Product>productList;

     public  void addProduct(Product product){
          if (productList==null){
               productList = new ArrayList<>();
          }
          product.setCategory(this);
          productList.add(product);

     }
     public void addAll(Product ... products){
          if (productList== null){
               productList = new ArrayList<>();
          }
          productList.addAll(Arrays.asList(products));
     }
     public void remove(Product product){
          product.setCategory(null);
          this.productList.remove(product);

     }

     public void  removeProducts(){
        Iterator<Product> iterator = this.productList.iterator();
        while (iterator.hasNext()){
             Product product = iterator.next();
             product.setCategory(null);
             iterator.remove();
        }
     }

}
