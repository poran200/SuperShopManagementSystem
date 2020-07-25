package com.example.supershop.services;

import com.example.supershop.exception.EntityAlreadyExistException;
import com.example.supershop.exception.EntityNotFoundException;
import com.example.supershop.model.Address;
import com.example.supershop.model.Product;
import com.example.supershop.model.Shop;
import com.example.supershop.repository.AddressRepository;
import com.example.supershop.repository.EmployeeRepository;
import com.example.supershop.repository.ProductRepository;
import com.example.supershop.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShopService {
    private final ShopRepository shopRepository;

    public final AddressRepository addressRepository;

    public final EmployeeRepository employeeRepository;

    public final ProductRepository productRepository;

    public ShopService(ShopRepository shopRepository,
                       AddressRepository addressRepository,
                       EmployeeRepository employeeRepository,
                       ProductRepository productRepository) {
        this.shopRepository = shopRepository;
        this.addressRepository = addressRepository;
        this.employeeRepository = employeeRepository;
        this.productRepository = productRepository;
    }

    public Shop  createShop(Shop shop) throws EntityAlreadyExistException {
        Optional<Shop> getShop = shopRepository.findById(shop.getShopId());
        if (getShop.isPresent()) throw  new EntityAlreadyExistException(getShop);
        else {
            Address address = addressRepository.save(shop.getShopAddress());
            shop.setShopAddress(address);
        }
//             addressRepository.save(shop.getEmployee().getAddress());
//             employeeRepository.save(shop.getEmployee());
            return shopRepository.save(shop);
    }
    public List<Shop> findAllShop(){
        return shopRepository.findAll();
    }

    public  Optional<Shop>  findById (long shopId) throws EntityNotFoundException {
        Optional<Shop> shop = shopRepository.findById(shopId);
        if (shop.isEmpty())
            throw  new EntityNotFoundException("Shop Not found : "+shopId);
        return shop;
    }

    public List<Shop> finnShopByCity(String cityName){
        return findAllShop()
                .stream()
                .filter(shop -> shop.getShopAddress()
                        .getCity().contains(cityName))
                .collect(Collectors.toList());
    }

    public  Shop updateShop(Shop shop) throws EntityNotFoundException {
        Optional<Shop> getShop = findById(shop.getShopId());
         shop.setShopId(getShop.get().getShopId());
        return shopRepository.save(shop);
    }

    public Optional<Product> addProductInShop(long shopId, long productId) throws EntityNotFoundException {
        Shop shop = shopRepository.getOne(shopId);
        Product product = productRepository.getOne(productId);

        if (shop == null){
            throw  new EntityNotFoundException(shop);
        }else {
            if (product == null){
               throw  new  EntityNotFoundException(product);
            }else {
                shop.addProduct(product);

            }
            return shopRepository.save(shop).getProductList().stream()
                    .filter(product1 -> product1.getProductId() == product.getProductId()).findFirst();
        }

    }
    public void deleteShop(long shopId) throws EntityNotFoundException {
        Optional<Shop> byId = findById(shopId);
        byId.ifPresent(shopRepository::delete);
        byId.orElseThrow(EntityNotFoundException::new);
    }

}
