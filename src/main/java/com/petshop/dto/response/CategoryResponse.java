package com.petshop.dto.response;

import com.petshop.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private Long id;
    private String categoryName;
    private String description;
    private ProductCategory.Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
