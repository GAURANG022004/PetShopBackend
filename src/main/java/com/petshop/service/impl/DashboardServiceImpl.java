package com.petshop.service.impl;

import com.petshop.dto.response.DashboardResponse;
import com.petshop.entity.Sale;
import com.petshop.entity.Stock;
import com.petshop.mapper.SaleMapper;
import com.petshop.repository.ProductCategoryRepository;
import com.petshop.repository.ProductRepository;
import com.petshop.repository.SaleRepository;
import com.petshop.repository.StockRepository;
import com.petshop.service.DashboardService;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;
    private final StockRepository stockRepository;
    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;

    public DashboardServiceImpl(ProductRepository productRepository,
                               ProductCategoryRepository categoryRepository,
                               StockRepository stockRepository,
                               SaleRepository saleRepository,
                               SaleMapper saleMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.stockRepository = stockRepository;
        this.saleRepository = saleRepository;
        this.saleMapper = saleMapper;
    }

    @Override
    public DashboardResponse getDashboardData() {
        DashboardResponse response = new DashboardResponse();

        response.setTotalProducts(productRepository.count());
        response.setTotalCategories(categoryRepository.count());
        
        List<Stock> allStocks = stockRepository.findAll();
        response.setTotalStockQuantity(allStocks.stream()
                .mapToInt(Stock::getCurrentStock)
                .sum());
        
        response.setLowStockProducts(allStocks.stream()
                .filter(stock -> stock.getCurrentStock() <= 10)
                .count());

        response.setTodaySales(saleRepository.countTodaySales());
        response.setMonthlyRevenue(saleRepository.sumMonthlyRevenue());
        response.setTotalRevenue(saleRepository.sumTotalRevenue());

        List<Sale> recentSales = saleRepository.findTodaySales();
        response.setRecentSales(recentSales.stream()
                .limit(5)
                .map(saleMapper::toResponse)
                .collect(Collectors.toList()));

        return response;
    }
}
