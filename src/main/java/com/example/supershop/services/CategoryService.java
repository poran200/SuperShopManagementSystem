package com.example.supershop.services;

import com.example.supershop.exception.EntityAlreadyExistException;
import com.example.supershop.model.Category;
import com.example.supershop.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {
    private  final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) throws EntityAlreadyExistException {
        Optional<Category> optionalCategory = categoryRepository.findById(category.getId());
        if (optionalCategory.isPresent()){
            throw  new EntityAlreadyExistException(category);
        }
        else
            return categoryRepository.save(category);
    }

    public void  deleteAllProducts(long categoryId){
        var categoryOptional = categoryRepository.findById(categoryId);
        categoryOptional.ifPresent(Category::removeProducts);
        categoryOptional.orElseThrow(()-> new EntityNotFoundException("Not found "));
    }

}
