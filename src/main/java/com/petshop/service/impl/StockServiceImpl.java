package com.petshop.service.impl;

import com.petshop.dto.request.DamagedStockRequest;
import com.petshop.dto.request.StockInRequest;
import com.petshop.dto.request.StockOutRequest;
import com.petshop.dto.response.StockResponse;
import com.petshop.entity.Product;
import com.petshop.entity.Stock;
import com.petshop.exception.InsufficientStockException;
import com.petshop.exception.ResourceNotFoundException;
import com.petshop.mapper.StockMapper;
import com.petshop.repository.ProductRepository;
import com.petshop.repository.StockRepository;
import com.petshop.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final StockMapper stockMapper;

    public StockServiceImpl(StockRepository stockRepository, 
                           ProductRepository productRepository,
                           StockMapper stockMapper) {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
        this.stockMapper = stockMapper;
    }

    @Override
    public StockResponse addStockIn(StockInRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", request.getProductId()));

        Stock stock = stockRepository.findByProductId(product.getId()).orElse(null);
        if (stock == null) {
            stock = new Stock();
            stock.setProduct(product);
            stock.setStockIn(request.getQuantity());
            stock.setCurrentStock(request.getQuantity());
        } else {
            stock.addStockIn(request.getQuantity());
        }

        product.setQuantity(stock.getCurrentStock());
        productRepository.save(product);
        stock = stockRepository.save(stock);

        return stockMapper.toResponse(stock);
    }

    @Override
    public StockResponse addStockOut(StockOutRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", request.getProductId()));

        Stock stock = stockRepository.findByProductId(product.getId()).orElse(null);
        if (stock == null) {
            throw new ResourceNotFoundException("Stock", "productId", request.getProductId().toString());
        }

        if (stock.getCurrentStock() < request.getQuantity()) {
            throw new InsufficientStockException(product.getProductName(), request.getQuantity(), stock.getCurrentStock());
        }

        stock.addStockOut(request.getQuantity());
        product.setQuantity(stock.getCurrentStock());
        productRepository.save(product);
        stock = stockRepository.save(stock);

        return stockMapper.toResponse(stock);
    }

    @Override
    public StockResponse addDamagedStock(DamagedStockRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", request.getProductId()));

        Stock stock = stockRepository.findByProductId(product.getId()).orElse(null);
        if (stock == null) {
            throw new ResourceNotFoundException("Stock", "productId", request.getProductId().toString());
        }

        if (stock.getCurrentStock() < request.getQuantity()) {
            throw new InsufficientStockException(product.getProductName(), request.getQuantity(), stock.getCurrentStock());
        }

        stock.addDamagedStock(request.getQuantity());
        product.setQuantity(stock.getCurrentStock());
        productRepository.save(product);
        stock = stockRepository.save(stock);

        return stockMapper.toResponse(stock);
    }

    @Override
    public List<StockResponse> getAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        return stocks.stream()
                .map(stockMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockResponse> getLowStockProducts() {
        List<Stock> stocks = stockRepository.findLowStockProducts();
        return stocks.stream()
                .map(stockMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public StockResponse getStockByProductId(Long productId) {
        Stock stock = stockRepository.findByProductId(productId).orElse(null);
        if (stock == null) {
            throw new ResourceNotFoundException("Stock", "productId", productId.toString());
        }
        return stockMapper.toResponse(stock);
    }
}
