package com.petshop.mapper;

import com.petshop.dto.request.ProductRequest;
import com.petshop.dto.response.ProductResponse;
import com.petshop.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest request) {
        if (request == null) {
            return null;
        }
        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setProductCode(request.getProductCode());
        product.setBarcode(request.getBarcode());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setGstPercentage(request.getGstPercentage());
        product.setDescription(request.getDescription());
        product.setQuantity(request.getQuantity());
        product.setStatus(Product.Status.ACTIVE);
        return product;
    }

    public ProductResponse toResponse(Product product) {
        if (product == null) {
            return null;
        }
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setProductName(product.getProductName());
        response.setProductCode(product.getProductCode());
        response.setBarcode(product.getBarcode());
        response.setBrand(product.getBrand());
        response.setPrice(product.getPrice());
        response.setGstPercentage(product.getGstPercentage());
        response.setDescription(product.getDescription());
        response.setQuantity(product.getQuantity());
        response.setStatus(product.getStatus());
        response.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
        response.setCategoryName(product.getCategory() != null ? product.getCategory().getCategoryName() : null);
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());
        return response;
    }

    public void updateEntityFromRequest(ProductRequest request, Product product) {
        if (request == null || product == null) {
            return;
        }
        product.setProductName(request.getProductName());
        product.setBarcode(request.getBarcode());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setGstPercentage(request.getGstPercentage());
        product.setDescription(request.getDescription());
        product.setQuantity(request.getQuantity());
    }
}
