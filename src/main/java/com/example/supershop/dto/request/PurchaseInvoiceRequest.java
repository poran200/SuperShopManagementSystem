package com.example.supershop.dto.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
@Data
public class PurchaseInvoiceRequest {

    @NotNull(message = "provider id must be need")
    private long providerId;
    @NotNull(message = "warehouse id must be need")
    private long warehouseId;
    @NotNull(message = "userId must be need")
    private long userId;
    @Min(value = 1, message = "Item at last one mandatory!")
    private List<@Valid ItemLineRequest> itemLineRequests;

}
