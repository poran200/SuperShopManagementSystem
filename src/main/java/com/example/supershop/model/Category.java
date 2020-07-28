package com.example.supershop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

import static javax.persistence.CascadeType.*;

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
     @GeneratedValue(strategy = GenerationType.AUTO)
     private long id;
     private String  categoryName;
     @OneToMany(mappedBy = "category", cascade = {PERSIST, MERGE,
             DETACH, REFRESH},orphanRemoval = true)
     @JsonIgnoreProperties(value = "category")
     @ToString.Exclude
     private List<Product>productList;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "sub_category")
     private Category subCategory;

     @OneToMany(mappedBy = "subCategory" ,fetch = FetchType.LAZY,cascade = {PERSIST,MERGE})
     @ToString.Exclude
     private Set<Category> subCategories;



     public void addSubCategory(Category category){
          if (subCategories == null){
               subCategories = new HashSet<>();
          }
          subCategory.setSubCategory(this);
          subCategories.add(category);
     }
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
