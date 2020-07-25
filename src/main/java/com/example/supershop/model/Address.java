package com.example.supershop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Address extends BaseModel implements Serializable  {
    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private  String city;
    private String details;

    public Address(String city, String details) {
        this.city = city;
        this.details= details;
    }
}
