package com.example.supershop.standard.services.impl;

import com.example.supershop.dto.request.CategoryDto;
import com.example.supershop.dto.respose.Response;
import com.example.supershop.model.Category;
import com.example.supershop.repository.CategoryRepository;
import com.example.supershop.standard.services.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.example.supershop.util.ResponseBuilder.*;

@Service("CategoryService")
@Log4j2
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private  final ModelMapper modelMapper;
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response create(CategoryDto categoryDto) {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
//        modelMapper.getConfiguration().setPropertyCondition(Conditions.isType(Category.class));
       try {
           var category = modelMapper.map(categoryDto, Category.class);
//           category.setSubCategory(categoryDto.getSuperCategory());
           var savedCategory = categoryRepository.save(category);
           return getSuccessResponse(HttpStatus.CREATED,"category created",savedCategory);
       }catch (Exception e){
          log.error(e.getMessage());
          return  getInternalServerError();
        }
    }

    @Override
    public Response getById(long id) {
       return categoryRepository.findByIdAndIsActiveTrue(id)
               .map(category -> getSuccessResponse(HttpStatus.OK,"category found",category))
               .orElse(getFailureResponse(HttpStatus.NOT_FOUND,"category not found"));
    }

    @Override
    public Response getAllByPage(Pageable pageable) {
        var page = categoryRepository.findAll(pageable);
        return getSuccessResponsePage(HttpStatus.OK,"Category page",page);
    }

    @Override
    public Response deleteById(long id) {
        var category = categoryRepository.findByIdAndIsActiveTrue(id);
        if (category.isPresent()){
            category.get().setIsActive(false);
            try{
                categoryRepository.save(category.get());
                return getSuccessResponse(HttpStatus.OK,"category deleted successfully",null);
            }catch (Exception e){
                log.error(e.getMessage());
                return getInternalServerError();
             }
        }
        return getFailureResponse(HttpStatus.NOT_FOUND,"category not found");
    }

    @Override
    public Response update(long id, Category category) {
        var categoryOptional = categoryRepository.findByIdAndIsActiveTrue(id);
        if (categoryOptional.isPresent()){
            try{
                var categoryGet = categoryOptional.get();
                categoryGet.setCategoryName(category.getCategoryName());
                categoryGet.addSubCategory(category);
                var saveCategory = categoryRepository.save(categoryGet);
                return getSuccessResponse(HttpStatus.OK,"Category updated",saveCategory);
            }catch (Exception e){
                log.error(e.getMessage());
                return getInternalServerError();
            }
        }
        return getFailureResponse(HttpStatus.NOT_FOUND,"Category not found Id: "+id);
    }

    @Override
    public Response getByCategoryName(String name,Pageable pageable) {
        var categoryList = categoryRepository.getCategoriesByCategoryName(name,pageable);
        return getSuccessResponseList(HttpStatus.OK,"list of category by name",categoryList,categoryList.size());
    }
}
