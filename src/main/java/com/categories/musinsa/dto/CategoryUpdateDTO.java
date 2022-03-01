package com.categories.musinsa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@NoArgsConstructor
@Getter @Setter
public class CategoryUpdateDTO {

    @NotNull
    private String categoryName;

    private Long upperCategoryId;

}
