package com.petshop.service;

import com.petshop.dto.request.ProductRequest;
import com.petshop.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse getProductById(Long id);

    List<ProductResponse> getAllProducts();

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

    List<ProductResponse> searchProductsByName(String name);

    List<ProductResponse> getProductsByCategory(Long categoryId);

    ProductResponse getProductByBarcode(String barcode);
}
