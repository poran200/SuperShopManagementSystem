package com.example.supershop.standard.services.impl;

import com.example.supershop.controller.ProductController;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

import static com.example.supershop.util.ResponseBuilder.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
                "Product created successfully", linkAdd(saveProduct));
    }

    @Override
    public Response update(Long id, CreateProductRequestDto productRequestDto) {
        var optionalProduct = productRepository.findByProductIdAndIsActiveTrue(id);
        if (optionalProduct.isPresent()) {
            var proUpdate = modelMapper.map(productRequestDto, Product.class);
            var product = optionalProduct.get();
//            BeanUtils.copyProperties(proUpdate,product);
            product.setProductName(proUpdate.getProductName());
            product.setExpireDate(proUpdate.getExpireDate());
            product.setDetails(proUpdate.getDetails());
            product.setPrice(proUpdate.getPrice());
            product.setVat(proUpdate.getVat());
            var category = categoryRepository.findByIdAndIsActiveTrue(proUpdate.getCategory().getId());
            if (category.isPresent()) {
                category.get().addProduct(product);
                var updateProduct = productRepository.save(product);
                return getSuccessResponse(HttpStatus.OK, "Product updated", linkAdd(updateProduct));
            }
            return getFailureResponse(HttpStatus.BAD_REQUEST, "category not found id: " + productRequestDto.getCategoryId());

        }
        return getFailureResponse(HttpStatus.BAD_REQUEST, "product not found Id: " + id);
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
            return getSuccessResponse(HttpStatus.OK, "product found", linkAdd(product));
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
        return getInternalServerError();
    }

    @Override
    public Response getAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        products.getContent().forEach(this::linkAddVoid);
        return getSuccessResponsePage(HttpStatus.OK, "page of product", products);

    }

    @Override
    public Response getAllByProductIsActiveTrue(Pageable pageable) {
        var productPage = productRepository.findAllByIsActiveTrue(pageable);
        return getSuccessResponsePage(HttpStatus.OK, "Products by pageable",
                getPageWithLink(productPage, productPage.getPageable()));
    }

    @Override
    public Response findAllByCategoryId(Pageable pageable, long categoryId) {
        Page<Product> page = productRepository.findAllByCategoryId(pageable, categoryId);
//                .map(product-> modelMapper.map(product, ProductResponseDto.class));
        return getSuccessResponsePage(HttpStatus.OK, "product", getPageWithLink(page, page.getPageable()));
    }

    private ProductResponseDto linkAdd(Product response) {
        ProductResponseDto productResponseDto = modelMapper.map(response, ProductResponseDto.class);
        productResponseDto.add(linkTo(ProductController.class).slash(productResponseDto.getId()).withSelfRel());
        Link categoryLink = linkTo(methodOn(ProductController.class)
                .getProductByPage(productResponseDto.getCategoryId(), 0, 20))
                .withRel("category");
        productResponseDto.add(categoryLink);
        return productResponseDto;
    }

    private void linkAddVoid(Product response) {
        ProductResponseDto productResponseDto = modelMapper.map(response, ProductResponseDto.class);
        productResponseDto.add(linkTo(ProductController.class).slash(productResponseDto.getId()).withSelfRel());
        Link categoryLink = linkTo(methodOn(ProductController.class)
                .getProductByPage(productResponseDto.getCategoryId(), 0, 20))
                .withRel("category");
        productResponseDto.add(categoryLink);

    }

    private Page<ProductResponseDto> getPageWithLink(Page<Product> page, Pageable pageable) {
        var dtoList = new ArrayList<ProductResponseDto>();
        for (Product product : page.getContent()) {
            var responseDto = linkAdd(modelMapper.map(product, Product.class));
            dtoList.add(responseDto);
        }
        return new PageImpl<>(dtoList, pageable, dtoList.size());
    }
}
