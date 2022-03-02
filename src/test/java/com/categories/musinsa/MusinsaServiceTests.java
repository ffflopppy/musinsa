package com.categories.musinsa;

import com.categories.musinsa.config.exception.MusinException;
import com.categories.musinsa.dto.CategoryDTO;
import com.categories.musinsa.entity.Categories;
import com.categories.musinsa.service.CategoryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class MusinsaServiceTests {


    @Autowired
    CategoryService categoryService;



    @Test
    @Transactional
    @Rollback(value = true)
    void isExistCategoy() {
        //given
        CategoryDTO saveSuccessDto = new CategoryDTO();
        saveSuccessDto.setCategoryName("A");
        //when
        Categories category = categoryService.isExistCategory(11L);
        //then
        Assertions.assertThat(category).isNull();
    }

    @Test
    @Transactional
    @Rollback(value = true)
    void testSave()  throws Exception{
        // 성공 : name이 있는경우
        //given
        CategoryDTO saveSuccessDto = new CategoryDTO();
        saveSuccessDto.setCategoryName("A");
        //when
        Long id = categoryService.saveCategory(saveSuccessDto);
        //then
        Assertions.assertThat(id).isGreaterThan(0);

        // 실패 : 상위 카테고리를 입력받았지만 해당 카테고리가 없는 경우
        //given
        CategoryDTO saveFailDto = new CategoryDTO();
        saveFailDto.setCategoryName("B");
        saveFailDto.setUpperCategoryId(99l);

        Assertions.assertThatThrownBy(() ->categoryService.saveCategory(saveFailDto))
                .isInstanceOf(MusinException.class);

    }

    @Test
    @Transactional
    void testList() {
        // 성공 : 전체 목록
        //given
        String keyword = new String();
        //when
        List<Categories> categories = categoryService.findCategories(keyword);
        //then
        Assertions.assertThat(categories.size()).isGreaterThan(0);

        // 성공 : 이름 조건 목록
        //given
        keyword = "Ko";
        //when
        List<Categories> categories2 = categoryService.findCategories(keyword);
        Assertions.assertThat(categories2.size()).isGreaterThan(0);

        // 성공 : 이름 조건이 목록이 0 인 경우
        //given
        keyword = "AA";
        //when
        List<Categories> categories3 = categoryService.findCategories(keyword);
        Assertions.assertThat(categories3.size()).isZero();

    }


    @Test
    @Transactional
    void testUpdate() {

        // 실패 : 상위 카테고리를 자식으로 넣으려고 할경우
        //given
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryName("USA");
        dto.setUpperCategoryId(7l);

        // when
        Assertions.assertThatThrownBy(() ->categoryService.updateCategory(dto, 2l))
                .isInstanceOf(MusinException.class);

        // 성공 : 정상 수정
        dto.setCategoryName("USA");
        dto.setUpperCategoryId(null);
        Categories category = categoryService.updateCategory(dto, 2l);
        Assertions.assertThat(category.getCategoryName()).isEqualTo("USA");


        // 실패 : 수정하려는 데이터가 없을경우
        Assertions.assertThatThrownBy(() ->categoryService.updateCategory(dto, 99l))
                .isInstanceOf(MusinException.class);



    }

    @Test
    @Transactional
    void testDelete() {

        // 성공 : 옵션이 true일 경우
        //given

        Boolean isDeleteAll = true;
        Long id = 1l;
        // when
        categoryService.deleteCategory(id, isDeleteAll);
        int size = categoryService.findCategories(null).size();
        // then
        Assertions.assertThat(size).isEqualTo(4);

        // 성공 : 옵션이 false일 경우
        //given
        isDeleteAll = false;
        id = 2l;
        // when
        categoryService.deleteCategory(id, isDeleteAll);
        int size2 = categoryService.findCategories(null).size();
        // then
        Assertions.assertThat(size2).isEqualTo(3);

        // 실패 : 삭제하려는 데이터가 없을경우
        // then
        Assertions.assertThatThrownBy(() ->categoryService.deleteCategory(99l, false))
                .isInstanceOf(MusinException.class);
    }
}
