package com.example.supershop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@EqualsAndHashCode( of = {"stockId"},callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WareHouseStock extends BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long stockId;
    private int quantity;
    @OneToOne
    private Product product;
    @OneToOne
    private WareHouse wareHouse;
}
