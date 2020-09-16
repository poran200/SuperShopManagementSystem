package com.example.supershop.dto.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class StockDto {
    private long stockId;
    @NotNull(message = "quantity not be null")
    @Min(value = 1, message = "quantity at last 1 item")
    private int quantity;
    @Valid
    private Product product;
    @Valid
    private Shop shop;


    @Data
    public static class Product {
        @NotNull(message = "id not be null")
        private long productId;
    }

    @Data
    public static class Shop {
        @NotNull(message = "id not be null")
        private long shopId;
    }
}
