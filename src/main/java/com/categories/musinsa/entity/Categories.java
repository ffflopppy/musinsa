package com.categories.musinsa.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
public class Categories {

    @Id @GeneratedValue
    private Long id;

    private Long upperCategoryId;

    @NotNull
    private String categoryName;


    @Builder.Default
    @OneToMany(mappedBy = "upperCategoryId", orphanRemoval = true)
    private List<Categories> children = new ArrayList<>();


}
