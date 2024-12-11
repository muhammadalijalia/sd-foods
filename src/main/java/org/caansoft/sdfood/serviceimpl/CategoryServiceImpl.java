package org.caansoft.sdfood.serviceimpl;

import org.caansoft.core.enums.Flag;
import org.caansoft.sdfood.prestashopIntegration.PrestashopCategory;
import org.caansoft.sdfood.prestashopIntegration.PrestashopService;
import org.caansoft.sdfood.repo.PrestashopCategoryRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.caansoft.sdfood.dto.CategoryDto;
import org.caansoft.sdfood.enums.ModelDtoConvertor;
import org.caansoft.sdfood.helper.GenericConverter;
import org.caansoft.sdfood.model.Category;
import org.caansoft.sdfood.repo.CategoryRepository;
import org.caansoft.sdfood.service.CategoryService;
import org.caansoft.core.dto.BaseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PrestashopService prestashopService;

    @Autowired
    PrestashopCategoryRepo prestashopCategoryRepository;

    @Override
    public <T extends BaseDto> T add(T dto) {
        CategoryDto categoryDto = (CategoryDto) dto;
        try {
            Category model = ModelDtoConvertor.CATEGORY.dtoToModel(categoryDto, Category.class);
            model = categoryRepository.save(model);

            if(categoryDto.getAction() != null && categoryDto.getAction().equalsIgnoreCase("PUBLISH")){
                PrestashopCategory prestashopCategory = prestashopService.addCategory(model);
                if(prestashopCategory != null){
                    model.setPrestashopCategoryId(prestashopCategory.getId());
                    categoryRepository.save(model);
                    categoryDto.setPrestashopCategoryId(prestashopCategory.getId());

                    org.caansoft.sdfood.model.PrestashopCategory prestCategoryModel = new org.caansoft.sdfood.model.PrestashopCategory();
                    prestCategoryModel.setId(prestashopCategory.getId().longValue());
//                    if(!prestashopCategory.getName().isEmpty() && !prestashopCategory.getName().get(0).getLanguage().isEmpty()){
//                        prestCategoryModel.setName(prestashopCategory.getName().get(0).getLanguage().get(0).getName());
//                    }
                    prestCategoryModel.setName(model.getName());
                    prestashopCategoryRepository.save(prestCategoryModel);
                }
            }
            categoryDto.setId(model.getId());
            return (T) categoryDto;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public <T extends BaseDto> Page<T> find(Pageable pageable) {
        Page<Category> all = categoryRepository.findAllByFlag(pageable, Flag.ACTIVE);
        if (all.getSize() > 0) {
            List<CategoryDto> list = new ArrayList<>();
            CategoryDto dto = null;
            for (Category category : all) {
                dto = new CategoryDto();
                BeanUtils.copyProperties(category, dto);
                list.add(dto);
            }
            return (Page<T>) new PageImpl<CategoryDto>(list, pageable, all.getTotalElements());
        }
        return null;
    }

    @Override
    public <T extends BaseDto> T findOne(long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent() && category.get().getFlag() == Flag.ACTIVE) {
            try {
                return ModelDtoConvertor.CATEGORY.modelToDto(category.get(), CategoryDto.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void delete(long id) {
        Category category = categoryRepository.findById(id).get();
        category.setFlag(Flag.DELETED);
        categoryRepository.save(category);
    }

    @Override
    public <T extends BaseDto> T update(T dto) {
//        try {
//            Category category = GenericConverter.CATEGORY.dtoToModel(dto, Category.class);
//            categoryRepository.save(category);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return add(dto);
    }
}
