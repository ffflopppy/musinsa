package com.categories.musinsa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@NoArgsConstructor
@Getter @Setter
public class CategoryDTO {

    @Schema(description = "카테고리 이름", nullable = false, example = "CATEGORY A")
    @NotNull
    private String categoryName;

    @Schema(description = "상위 카테고리 ID", nullable = true, example = "1")
    private Long upperCategoryId;

}
