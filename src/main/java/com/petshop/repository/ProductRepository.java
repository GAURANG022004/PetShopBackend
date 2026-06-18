package com.petshop.repository;

import com.petshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductCode(String productCode);

    Optional<Product> findByBarcode(String barcode);

    boolean existsByProductCode(String productCode);

    boolean existsByBarcode(String barcode);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByStatus(Product.Status status);

    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE' AND p.category.id = :categoryId")
    List<Product> findActiveProductsByCategory(@Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE' AND LOWER(p.productName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> searchByName(@Param("name") String name);

    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE' AND (LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.productCode) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Product> searchByKeyword(@Param("keyword") String keyword);
}
