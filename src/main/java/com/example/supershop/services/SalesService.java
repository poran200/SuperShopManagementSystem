package com.example.supershop.services;

import com.example.supershop.exception.EntityNotFoundException;
import com.example.supershop.model.*;
import com.example.supershop.repository.SaleInvoiceLineItemRepository;
import com.example.supershop.repository.SaleInvoiceRepository;
import com.example.supershop.standard.services.ShopStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SalesService {
    Logger logger = LoggerFactory.getLogger(SalesService.class);
    private  final SaleInvoiceLineItemRepository saleInvoiceLineItemRepository;
    private final SaleInvoiceRepository invoiceRepository;
    private final ShopStockService stockService;
    private final UserService userService;
    private final ShopService shopService;

    public SalesService(SaleInvoiceLineItemRepository saleInvoiceLineItemRepository,
                        SaleInvoiceRepository invoiceRepository, ShopStockService stockService,
                        UserService userService, ShopService shopService) {
        this.saleInvoiceLineItemRepository = saleInvoiceLineItemRepository;
        this.invoiceRepository = invoiceRepository;
        this.stockService = stockService;
        this.userService = userService;
        this.shopService = shopService;
    }

    public SalesInvoice createSalesInvoice(List<SaleInvoiceLineItem> lineItems, long shopId,long userId){
       SalesInvoice salesInvoice = new SalesInvoice();
        User user = userService.gerById(userId);
        Optional<Shop> shop = shopService.findById(shopId);
        if ((user != null) && (shop.isPresent())){
            salesInvoice.setUser(user);
            logger.info("userId find{}",userId);
            salesInvoice.setShop(shop.get());
            logger.info("shop find{}",shopId);
            lineItems.forEach(saleInvoiceLineItem -> {
                Product product = saleInvoiceLineItem.getProduct();
                int productQuantity = saleInvoiceLineItem.getProductQuantity();
                stockService.updateStock(shopId,product.getProductId(),productQuantity);
            });
            double totalBill = lineItems.stream()
                    .mapToDouble(SaleInvoiceLineItem::getCalculatePrice).sum();
            salesInvoice.setTotalBill(totalBill);
            double totalVat = lineItems.stream().mapToDouble(SaleInvoiceLineItem::getTotalVat).sum();
            salesInvoice.setTotalVat(totalVat);
            salesInvoice.setInvoiceItems(lineItems);
            lineItems.forEach(saleInvoiceLineItem -> saleInvoiceLineItem.setInvoice(salesInvoice));
            SalesInvoice saveInvoice = invoiceRepository.save(salesInvoice);
            saleInvoiceLineItemRepository.saveAll(lineItems);
            logger.info("SaveInvoice{}",saveInvoice.getSaleInvoiceId()+" bill: "+ saveInvoice.getTotalBill());
            return salesInvoice;
        }else {
            throw  new EntityNotFoundException("User Not Found : "+userId);
        }

    }
}
