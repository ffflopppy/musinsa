package com.categories.musinsa.controller;

import com.categories.musinsa.dto.CategoryDTO;
import com.categories.musinsa.entity.Categories;
import com.categories.musinsa.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/category/save")
    public void createCategory(@Valid @RequestBody CategoryDTO dto) {
        categoryService.saveCategory(dto);
    }

    @GetMapping("categories")
    public List<Categories> categories(@Nullable String keyword) {
        List<Categories> categories =categoryService.findCategories(keyword);
        return categories;
    }

    @PatchMapping("/category/{id}")
    public void updateCategory(@Valid @RequestBody CategoryDTO dto, @PathVariable("id") Long id) {
        categoryService.updateCategory(dto, id);
    }

    @DeleteMapping("/category/{id}")
    public void deleteCategory(@PathVariable("id") Long id
            , @RequestParam("isDeleteAll") Boolean isDeleteAll ) {
        categoryService.deleteCategory( id, isDeleteAll);
    }
}
