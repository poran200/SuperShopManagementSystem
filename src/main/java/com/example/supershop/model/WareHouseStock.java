package com.example.supershop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonIgnoreProperties(value = "category")
    private Product product;
    @OneToOne
    @JsonIgnoreProperties({"products","address","manager"})
    private WareHouse wareHouse;
}
