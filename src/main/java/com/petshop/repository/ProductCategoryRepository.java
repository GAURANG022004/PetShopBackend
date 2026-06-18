package com.petshop.repository;

import com.petshop.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    Optional<ProductCategory> findByCategoryName(String categoryName);

    boolean existsByCategoryName(String categoryName);

    List<ProductCategory> findByStatus(ProductCategory.Status status);

    @Query("SELECT c FROM ProductCategory c WHERE c.status = 'ACTIVE' ORDER BY c.categoryName")
    List<ProductCategory> findAllActiveCategories();
}
