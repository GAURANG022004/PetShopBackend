package com.petshop.service.impl;

import com.petshop.dto.request.ProductRequest;
import com.petshop.dto.response.ProductResponse;
import com.petshop.entity.Product;
import com.petshop.entity.ProductCategory;
import com.petshop.entity.Stock;
import com.petshop.exception.DuplicateResourceException;
import com.petshop.exception.ResourceNotFoundException;
import com.petshop.mapper.ProductMapper;
import com.petshop.repository.ProductCategoryRepository;
import com.petshop.repository.ProductRepository;
import com.petshop.repository.StockRepository;
import com.petshop.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;
    private final StockRepository stockRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, 
                             ProductCategoryRepository categoryRepository,
                             StockRepository stockRepository,
                             ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.stockRepository = stockRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        if (productRepository.existsByProductCode(request.getProductCode())) {
            throw new DuplicateResourceException("Product", "productCode", request.getProductCode());
        }

        if (request.getBarcode() != null && productRepository.existsByBarcode(request.getBarcode())) {
            throw new DuplicateResourceException("Product", "barcode", request.getBarcode());
        }

        ProductCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("ProductCategory", request.getCategoryId()));

        Product product = productMapper.toEntity(request);
        product.setCategory(category);
        product = productRepository.save(product);

        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setCurrentStock(request.getQuantity());
        stock.setStockIn(request.getQuantity());
        stockRepository.save(stock);

        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
        return productMapper.toResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));

        if (!product.getProductCode().equals(request.getProductCode()) &&
                productRepository.existsByProductCode(request.getProductCode())) {
            throw new DuplicateResourceException("Product", "productCode", request.getProductCode());
        }

        if (request.getBarcode() != null && !request.getBarcode().equals(product.getBarcode()) &&
                productRepository.existsByBarcode(request.getBarcode())) {
            throw new DuplicateResourceException("Product", "barcode", request.getBarcode());
        }

        ProductCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("ProductCategory", request.getCategoryId()));

        productMapper.updateEntityFromRequest(request, product);
        product.setCategory(category);
        product = productRepository.save(product);

        Stock stock = stockRepository.findByProductId(product.getId()).orElse(null);
        if (stock != null) {
            stock.setCurrentStock(request.getQuantity());
            stockRepository.save(stock);
        }

        return productMapper.toResponse(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
        productRepository.delete(product);
    }

    @Override
    public List<ProductResponse> searchProductsByName(String name) {
        List<Product> products = productRepository.searchByName(name);
        return products.stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return products.stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductByBarcode(String barcode) {
        Product product = productRepository.findByBarcode(barcode)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "barcode", barcode));
        return productMapper.toResponse(product);
    }
}
