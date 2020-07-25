package com.example.supershop.standard.services.impl;

import com.example.supershop.dto.request.CreateProductRequestDto;
import com.example.supershop.dto.respose.ProductResponseDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.exception.EntityNotFoundException;
import com.example.supershop.model.Category;
import com.example.supershop.model.Product;
import com.example.supershop.repository.CategoryRepository;
import com.example.supershop.repository.ProductRepository;
import com.example.supershop.repository.ShopRepository;
import com.example.supershop.repository.StockRepository;
import com.example.supershop.standard.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.supershop.util.ResponseBuilder.*;

@Service("ProductService")
public class ProductServiceImpl implements ProductService {
    private  final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final ShopRepository shopRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, StockRepository stockRepository,
                              ShopRepository shopRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
        this.shopRepository = shopRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response save(CreateProductRequestDto productRequestDto) {
        Optional<Category> category = categoryRepository.findById(productRequestDto.getCategoryId());
        Optional<Product> optionalProduct;
        Product saveProduct;
        Product product = modelMapper.map(productRequestDto, Product.class);
        if (category.isPresent()){
            optionalProduct = productRepository.findById(productRequestDto.getId());
             if (optionalProduct.isPresent()){
                 return getFailureResponse(HttpStatus.NOT_ACCEPTABLE,
                         "Product already exist! Id: "+productRequestDto.getId());
             }else {
                 category.get().addProduct(product);
                 product.setCategory(category.get());
                 saveProduct = productRepository.save(product);
             }
             categoryRepository.save(category.get());
        } else {
            return getFailureResponse(HttpStatus.NOT_FOUND,
                    "category is Not found Id: "+productRequestDto.getId());
        }
        return getSuccessResponse(HttpStatus.CREATED,
                "Product created successfully", saveProduct);
    }

    @Override
    public Response update(Long Id, CreateProductRequestDto productRequestDto) {
        return null;
    }

    @Override
    public Response getByProductName(String productName) {
        return null;
    }

    @Override
    public Response delete(Long Id) {
        return null;
    }

    @Override
    public Response getById(Long id) {
        Optional<Product> optionalProduct = productRepository.findByProductIdAndIsActiveTrue(id);
        if (optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            ProductResponseDto dto = modelMapper.map(product, ProductResponseDto.class);
            return getSuccessResponse(HttpStatus.OK,"product found",dto);
        }
        return getFailureResponse(HttpStatus.NOT_FOUND,"product NOt found Id: "+id);
    }

    @Override
    public Product getByIdIsActiveTrue(long id) {
        var optionalProduct = productRepository.findByProductIdAndIsActiveTrue(id);
        if (optionalProduct.isPresent()){
            return optionalProduct.get();
        }
        throw  new EntityNotFoundException("product not found Id:  "+id);
    }

    @Override
    public Response getAll() {
        return null;
    }

    @Override
    public Response getAll(Pageable pageable) {
        Page<?> products = productRepository.findAll(pageable).map(product -> modelMapper.map(product,ProductResponseDto.class));
        return getSuccessResponsePage(HttpStatus.OK,"page of product",products);

    }

    @Override
    public Response getAllByProductIsActiveTrue(Pageable pageable) {
        return null;
    }

    @Override
    public Response findAllByCategoryId(Pageable pageable, long categoryId) {
        Page<ProductResponseDto> allByShopShopId = productRepository.findAllByCategoryId(pageable, categoryId)
                .map(product-> modelMapper.map(product, ProductResponseDto.class));

        return getSuccessResponsePage(HttpStatus.OK,"product",allByShopShopId);
    }


}
