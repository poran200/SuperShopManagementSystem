package com.example.supershop.dto.respose;

import com.example.supershop.model.WareHouse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ParchedInvoiceResponseDto extends RepresentationModel<ParchedInvoiceResponseDto> {
    private long id;
    protected double totalVat;
    protected double totalDiscount;
    protected double totalBill;
    private Date crateDate;
    @JsonIgnoreProperties({"invoice"})
    private List<@Valid LineItemResponse> invoiceItems;
    @JsonIgnore
    private WareHouse wareHouse;
    private String providerName;

    @JsonProperty(value = "warehouseName")
    private String getWarehouseName() {
        return wareHouse.getName();
    }

}