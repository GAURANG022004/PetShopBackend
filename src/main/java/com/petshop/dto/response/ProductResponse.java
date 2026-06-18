package com.petshop.dto.response;

import com.petshop.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String productName;
    private String productCode;
    private String barcode;
    private String brand;
    private BigDecimal price;
    private Double gstPercentage;
    private String description;
    private Integer quantity;
    private Product.Status status;
    private Long categoryId;
    private String categoryName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
