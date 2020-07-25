package com.example.supershop.standard.services.impl;

import com.example.supershop.dto.request.SalesInvoiceRequest;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.exception.EntityNotFoundException;
import com.example.supershop.exception.StockNOtAvailabelException;
import com.example.supershop.model.*;
import com.example.supershop.repository.*;
import com.example.supershop.standard.services.ProductSaleService;
import com.example.supershop.standard.services.ProductService;
import com.example.supershop.standard.services.ShopStockService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.supershop.util.ResponseBuilder.getFailureResponse;
import static com.example.supershop.util.ResponseBuilder.getSuccessResponse;

@Service("ProductSaleService")
public class ProductSaleServiceImpl implements ProductSaleService {
    private  final SaleInvoiceLineItemRepository saleInvoiceLineItemRepository;
    private final SaleInvoiceRepository invoiceRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    private final ShopStockService shopStockService;
    private final ProductService productService;

    public ProductSaleServiceImpl(SaleInvoiceLineItemRepository saleInvoiceLineItemRepository,
                                  SaleInvoiceRepository invoiceRepository,
                                   UserRepository userRepository,
                                  ShopRepository shopRepository, ProductRepository productRepository,
                                  ShopStockService shopStockService, ProductService productService) {
        this.saleInvoiceLineItemRepository = saleInvoiceLineItemRepository;
        this.invoiceRepository = invoiceRepository;
        this.shopStockService = shopStockService;
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @Override
    public Response createSaleInvoice( SalesInvoiceRequest invoiceRequest) {
        SalesInvoice salesInvoice = new SalesInvoice();
        List<SaleInvoiceLineItem> lineItems = new ArrayList<>();
        Optional<User> optionalUser = userRepository.findById(invoiceRequest.getUserId());
        if (invoiceRequest.getItemLineRequests().isEmpty()){
            return getFailureResponse(HttpStatus.BAD_REQUEST,"Product list not  empty");
        }
        if (optionalUser.isPresent()){
            salesInvoice.setUser(optionalUser.get());
            Optional<Shop> optionalShop = shopRepository.findById(invoiceRequest.getShopId());
            if (optionalShop.isPresent()){
                salesInvoice.setShop(optionalShop.get());
            }else {
                return getFailureResponse(HttpStatus.NOT_FOUND,
                                "shop not found :"+invoiceRequest.getShopId());
            }
        } else {
            return getFailureResponse(HttpStatus.NOT_FOUND,
                            "user not found :"+invoiceRequest.getUserId());

        }

        try {
            invoiceRequest.getItemLineRequests().forEach(lineRequest -> {
                Optional<Product> product = productRepository.findById(lineRequest.getProductId());
                product.ifPresent(value -> lineItems.add(new SaleInvoiceLineItem(product.get(), lineRequest.getQuantity())));
                product.orElseThrow(()-> new EntityNotFoundException("product not found: "+lineRequest.getProductId()));
                shopStockService.updateStock(invoiceRequest.getShopId(),product.get().getProductId(),lineRequest.getQuantity());
            });
        } catch (EntityNotFoundException | StockNOtAvailabelException e){
            return getFailureResponse(HttpStatus.NOT_FOUND,e.getMessage());
        }

        SalesInvoice invoice = invoiceRepository.save(calculate(salesInvoice,lineItems));
        saleInvoiceLineItemRepository.saveAll(lineItems);
        return  getSuccessResponse(HttpStatus.CREATED,"Invoice created",invoice);


    }

    @Override
    public Response getInvoiceById(int invoiceId) {
        var invoice = invoiceRepository.findById(invoiceId);
        return invoice.map(salesInvoice -> getSuccessResponse(HttpStatus.OK, "invoice found", salesInvoice))
                .orElse( getFailureResponse(HttpStatus.NOT_FOUND,"Invoice not found"));
    }

    @Override
    public Response getInvoiceByPage(Pageable pageable) {
        return null;
    }

    @Override
    public Response updateInvoice(Long invoiceId, SalesInvoice invoice) {
        return null;
    }


    @Override
    public SalesInvoice calculate(SalesInvoice salesInvoice, List<SaleInvoiceLineItem> lineItems) {
        salesInvoice.setTotalBill(lineItems.stream().mapToDouble(SaleInvoiceLineItem::getCalculatePrice).sum());
        salesInvoice.setTotalVat(lineItems.stream().mapToDouble(SaleInvoiceLineItem::getTotalVat).sum());
        salesInvoice.setInvoiceItems(lineItems);
        return salesInvoice;
    }

}
