package com.categories.musinsa.repository;

import com.categories.musinsa.entity.Categories;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CategorySpecification {

    public static Specification<Categories> noParam() {
        return new Specification<Categories>() {
            @Override
            public Predicate toPredicate(Root<Categories> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                return null;
            }
        };
    }

    public static Specification<Categories> likeCategoryName(String keyword) {
        return new Specification<Categories>() {
            @Override
            public Predicate toPredicate(Root<Categories> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // 2) like
                return criteriaBuilder.like(root.get("categoryName"), "%" + keyword + "%");
            }
        };
    }


}
