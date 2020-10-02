package com.example.supershop.standard.services.impl;

import com.example.supershop.dto.request.ShopDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.model.Shop;
import com.example.supershop.repository.AddressRepository;
import com.example.supershop.repository.ShopRepository;
import com.example.supershop.standard.services.ShopService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.example.supershop.util.ResponseBuilder.*;

@Service
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;
    private final AddressRepository addressRepository;

    public ShopServiceImpl(ShopRepository shopRepository, ModelMapper modelMapper, AddressRepository addressRepository) {
        this.shopRepository = shopRepository;
        this.modelMapper = modelMapper;
        this.addressRepository = addressRepository;
    }

    @Override
    public Response create(ShopDto shopDto) {
        var shop = modelMapper.map(shopDto, Shop.class);
        var address = addressRepository.save(shop.getShopAddress());
        shop.setShopAddress(address);
        var saveShop = shopRepository.save(shop);
        if (saveShop != null) {
            return getSuccessResponse(HttpStatus.CREATED, "shop created", modelMapper.map(saveShop, ShopDto.class));

        }
        return getFailureResponse(HttpStatus.BAD_REQUEST, "Shop is already exist");
    }

    @Override
    public Response getAllAndIsActiveTrue(Pageable pageable) {
        var shopPage = shopRepository.findAll(pageable)
                .map(shop -> modelMapper.map(shop, ShopDto.class));
        return getSuccessResponsePage(HttpStatus.OK, "shop by page", shopPage);
    }

    @Override
    public Response getById(long id) {
        var shopOptional = shopRepository.findById(id);
        if (shopOptional.isPresent()) {
            return getSuccessResponse(HttpStatus.OK, "shop found", modelMapper.map(shopOptional.get(), ShopDto.class));
        }
        return getFailureResponse(HttpStatus.NOT_FOUND, "shop not found id: " + id);
    }

    @Override
    public Response getByName(String name) {
        return null;
    }

    @Override
    public Response getByIdAndIsActiveTrue(long id) {
        return null;
    }

    @Override
    public Response addProductInWareHouse(long wareHouseId, long productId) {
        return null;
    }
}
