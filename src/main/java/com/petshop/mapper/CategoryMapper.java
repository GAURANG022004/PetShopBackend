package com.petshop.mapper;

import com.petshop.dto.request.CategoryRequest;
import com.petshop.dto.response.CategoryResponse;
import com.petshop.entity.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public ProductCategory toEntity(CategoryRequest request) {
        if (request == null) {
            return null;
        }
        ProductCategory category = new ProductCategory();
        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());
        category.setStatus(ProductCategory.Status.ACTIVE);
        return category;
    }

    public CategoryResponse toResponse(ProductCategory category) {
        if (category == null) {
            return null;
        }
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setCategoryName(category.getCategoryName());
        response.setDescription(category.getDescription());
        response.setStatus(category.getStatus());
        response.setCreatedAt(category.getCreatedAt());
        response.setUpdatedAt(category.getUpdatedAt());
        return response;
    }

    public void updateEntityFromRequest(CategoryRequest request, ProductCategory category) {
        if (request == null || category == null) {
            return;
        }
        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());
    }
}
