package com.example.supershop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Discount {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private  int id;
     private  String discountName;
     private  double percentage=0.0;
//     @OneToOne(fetch = FetchType.LAZY)
//     @MapsId
     private  Product product;
}
