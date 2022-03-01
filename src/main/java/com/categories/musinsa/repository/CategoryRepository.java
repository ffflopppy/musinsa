package com.categories.musinsa.repository;

import com.categories.musinsa.entity.Categories;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Long> , JpaSpecificationExecutor<Categories> {

    List<Categories> findAllByUpperCategoryId(Long uppperCategoryId);
}
