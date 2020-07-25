package com.example.supershop.services;

import com.example.supershop.exception.EntityAlreadyExistException;
import com.example.supershop.model.Provider;
import com.example.supershop.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProviderService {
private  final ProviderRepository providerRepository;

    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    public Provider createProvider(Provider provider) throws EntityAlreadyExistException {
        Optional<Provider> providerOptional = providerRepository.findById(provider.getId());
        if (providerOptional.isPresent()){
            throw  new EntityAlreadyExistException(provider);
        }
         return  providerRepository.save(provider);
    }

    public Optional<Provider> findById(long providerId){
        return providerRepository.findById(providerId);
    }

    public  List<Provider>  findAllProvider(){
        return providerRepository.findAll();
    }
    public List<Provider> findByCity(String cityName){
        return providerRepository.findAll().stream()
                .filter(provider -> provider.getAddress().getCity().equals(cityName))
                .collect(Collectors.toList());
    }

}
