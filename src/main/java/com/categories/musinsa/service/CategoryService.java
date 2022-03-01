package com.categories.musinsa.service;

import com.categories.musinsa.config.exception.MusinException;
import com.categories.musinsa.config.exception.MusinExceptionCodeEnum;
import com.categories.musinsa.dto.CategoryDTO;
import com.categories.musinsa.entity.Categories;
import com.categories.musinsa.repository.CategoryRepository;
import com.categories.musinsa.repository.CategorySpecification;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoriesRepository;

    public void deleteCategory(Long id, Boolean isDeleteAll) {
        // 삭제할 대상 entity
        Categories category = this.isExistCategory(id);

        if (category != null){

            // 대상의 자식노드
            List<Categories> categories = categoriesRepository.findAllByUpperCategoryId(id);
            if (categories.size() > 0) {
                // 대상만 삭제하는 옵션
                if (isDeleteAll == false) {
                        categories.forEach(
                                o -> {
                                    o.setUpperCategoryId(category.getUpperCategoryId() == null ? null : category.getUpperCategoryId());
                                    categoriesRepository.flush();
                                }
                        );
                } else { // 모두 삭제하는 옵션
                 categories.forEach(
                            o -> {
                                recursiveDelete(o.getId());

                            }
                    );
                }
            }


            categoriesRepository.deleteById(category.getId());
        }else { // 삭제하려는 카테고리가 없으면 에러.
            throw new MusinException(MusinExceptionCodeEnum.DATA_NOT_FOUND, MusinExceptionCodeEnum.DATA_NOT_FOUND.getMessage());
        }

        int size =categoriesRepository.findAll().size();
    }

    public void recursiveDelete(Long id) {
        categoriesRepository.deleteById(id);
        List<Categories> categoriesAll = categoriesRepository.findAllByUpperCategoryId(id);
        if (categoriesAll == null || categoriesAll.size() == 0) {
            return;
        }
        categoriesAll.forEach(
                o -> {
                    categoriesRepository.delete(o);
                    recursiveDelete(o.getId());
                }
        );
    }

    public Categories updateCategory(CategoryDTO dto, Long id) {
        Categories category =  this.isExistCategory(id);
        if (category != null){

           // 상위 카테고리를 수정하려는 경우
           if(dto.getUpperCategoryId()!= null){
               // 원하는 상위 카테고리가 현재 카테고리의 자식카테고리면 에러.
               category.getChildren().forEach(o->{
                    if (dto.getUpperCategoryId().equals(o.getId())){
                        throw new MusinException(MusinExceptionCodeEnum.CAN_NOT_EDIT, MusinExceptionCodeEnum.CAN_NOT_EDIT.getMessage());
                    }
               });
               category.setUpperCategoryId(dto.getUpperCategoryId());
           }

           category.setCategoryName(dto.getCategoryName());

           return categoriesRepository.findById(category.getId()).get();

       } else { // 수정 하려는 카테고리가 없으면 에러.
           throw new MusinException(MusinExceptionCodeEnum.DATA_NOT_FOUND, MusinExceptionCodeEnum.DATA_NOT_FOUND.getMessage());
       }
    }

    //카테고리 목록
    public Long saveCategory(CategoryDTO dto) {
        Categories category = this.isExistCategory(dto.getUpperCategoryId());
        if (dto.getUpperCategoryId() == null || category != null ) {
            category.setCategoryName(dto.getCategoryName());
            category.setUpperCategoryId(dto.getUpperCategoryId() == null ? null : dto.getUpperCategoryId());
            return categoriesRepository.save(category).getId();
        } else {
            throw new MusinException(MusinExceptionCodeEnum.NOT_FOUND_UPPER_CATEGORY, MusinExceptionCodeEnum.NOT_FOUND_UPPER_CATEGORY.getMessage());

        }
    }

    //카테고리 목록
    public List<Categories> findCategories(String keyword) {

        Specification<Categories> spec = Specification.where(CategorySpecification.noParam());

        if(!StringUtils.isBlank(keyword)) {
           spec = spec.and(CategorySpecification.likeCategoryName(keyword));
        }
        return categoriesRepository.findAll(spec);
    }

    public Categories isExistCategory(Long id) {
        Optional<Categories> categories = categoriesRepository.findById(id);
        if (categories.isPresent()){
            return categories.get();
        } else {
            return null;
        }

    }

}
