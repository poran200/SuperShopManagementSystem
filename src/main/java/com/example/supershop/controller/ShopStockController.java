package com.example.supershop.controller;

import com.example.supershop.anotation.APiController;
import com.example.supershop.anotation.DataValidation;
import com.example.supershop.dto.request.StockDto;
import com.example.supershop.standard.services.ShopStockService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.supershop.util.UrlConstrains.ShopStockManagement.*;

@APiController
@RequestMapping(ROOT)
@Log4j2
public class ShopStockController {
    private final ShopStockService shopStockService;

    public ShopStockController(ShopStockService shopStockService) {
        this.shopStockService = shopStockService;
    }

    @PostMapping(CREATE)
    @DataValidation
    public ResponseEntity<Object> create(@RequestBody @Valid StockDto stockDto,
                                         BindingResult bindingResult) {
        var response = shopStockService.createStock(stockDto);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    @GetMapping(FIND_BY_ID)
    public ResponseEntity<Object> findById(@PathVariable long id) {
        var response = shopStockService.getStockById(id);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    @GetMapping(ALL_BY_PAGE)
    public ResponseEntity<Object> getStockByPage(@PathVariable long shopId,
                                                 @RequestParam(required = false) Pageable pageable) {
        var response = shopStockService.getStockByPage(shopId, pageable);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }

    @GetMapping(FIND_BY_SHOP_ID_AND_CATEGORY_ID)
    public ResponseEntity<Object> getAllShopAndCategory(@PathVariable long shopId,
                                                        @PathVariable long categoryId, @RequestParam(required = false) Pageable pageable) {
        var response = shopStockService.findAllByShopAndCategoryId(shopId, categoryId, pageable);
        return ResponseEntity.status((int) response.getStatusCode()).body(response);
    }
}
