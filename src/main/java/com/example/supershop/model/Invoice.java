package com.example.supershop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
public class Invoice extends BaseModel implements Serializable {
    private static final long serialVersionUID=1L;
    protected   double totalVat;
    protected   double totalDiscount;
    protected   double totalBill;
    private Date crateDate;
    @PrePersist
    private void onCreate(){
        crateDate = new Date();
    }

}
