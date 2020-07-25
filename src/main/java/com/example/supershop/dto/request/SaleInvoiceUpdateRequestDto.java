package com.example.supershop.dto.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
@Data
public class SaleInvoiceUpdateRequestDto {
    @NotNull(message = "shopId mandatory")
    long shopId;
//    @NotNull(message = "user id mandatory")
//    long  userId;
    @NotNull
    List< @Valid ItemLineUpdateRequest> itemLineRequests;
}
