package com.example.supershop.dto.request;

import com.example.supershop.model.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
@Data
public class  CreateProductRequestDto {

   // @NotEmpty(message = "product id empty is no acceptable")
    @NotNull(message = " product Id  mandatory")
    private long id;
    @Size(min = 3,max = 20 , message = "product name size at last 3 and max 20(char)")
    private String productName;
    @NotEmpty(message = "product details mandatory")
    private String details;
    @NotNull(message = "expireDate is mandatory")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expireDate;

    private Product.Price price;
    private  double vat;
    @NotNull(message = "category id null not accept ")
    private long categoryId;

    @Data
    public static class Price{
        @NotNull(message = "price not empty")
        private  double purchasePrice;
        @NotNull(message = "salePrice not empty")
        private  double sellPrice;
    }
}
