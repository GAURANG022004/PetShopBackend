package com.petshop.service.impl;

import com.petshop.dto.response.ProfitReportResponse;
import com.petshop.dto.response.SalesReportResponse;
import com.petshop.dto.response.StockReportResponse;
import com.petshop.entity.Product;
import com.petshop.entity.Sale;
import com.petshop.entity.Stock;
import com.petshop.repository.SaleRepository;
import com.petshop.repository.StockRepository;
import com.petshop.service.ReportService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final SaleRepository saleRepository;
    private final StockRepository stockRepository;
   

    public ReportServiceImpl(SaleRepository saleRepository,
                             StockRepository stockRepository) {
        this.saleRepository = saleRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public SalesReportResponse getSalesReport(LocalDateTime startDate, LocalDateTime endDate) {
        List<Sale> sales = saleRepository.findSalesByDateRange(startDate, endDate);
        
        SalesReportResponse response = new SalesReportResponse();
        response.setTotalSales((long) sales.size());
        response.setRevenue(sales.stream()
                .map(Sale::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        response.setTotalItemsSold(sales.stream()
                .mapToInt(Sale::getTotalItems)
                .sum());
        
        return response;
    }

    @Override
    public List<StockReportResponse> getStockReport() {
        List<Stock> stocks = stockRepository.findAll();
        
        return stocks.stream()
                .map(stock -> {
                    StockReportResponse response = new StockReportResponse();
                    response.setProductId(stock.getProduct().getId());
                    response.setProductName(stock.getProduct().getProductName());
                    response.setProductCode(stock.getProduct().getProductCode());
                    response.setCurrentStock(stock.getCurrentStock());
                    response.setIsLowStock(stock.getCurrentStock() <= 10);
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProfitReportResponse getProfitReport(LocalDateTime startDate, LocalDateTime endDate) {
        List<Sale> sales = saleRepository.findSalesByDateRange(startDate, endDate);
        
        BigDecimal revenue = sales.stream()
                .map(Sale::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal cost = BigDecimal.ZERO;
        
        for (Sale sale : sales) {
            for (var saleItem : sale.getSaleItems()) {
                Product product = saleItem.getProduct();
                BigDecimal itemCost = product.getPrice().multiply(new BigDecimal(saleItem.getQuantity()));
                cost = cost.add(itemCost);
            }
        }
        
        BigDecimal profit = revenue.subtract(cost);
        Double profitPercentage = cost.compareTo(BigDecimal.ZERO) > 0 
                ? profit.divide(cost, 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).doubleValue()
                : 0.0;
        
        ProfitReportResponse response = new ProfitReportResponse();
        response.setRevenue(revenue);
        response.setCost(cost);
        response.setProfit(profit);
        response.setProfitPercentage(profitPercentage);
        
        return response;
    }

    @Override
    public byte[] exportSalesReportToPdf(LocalDateTime startDate, LocalDateTime endDate) {
        throw new RuntimeException("PDF export not implemented yet. Requires JasperReports template configuration.");
    }

    @Override
    public byte[] exportSalesReportToExcel(LocalDateTime startDate, LocalDateTime endDate) {
        throw new RuntimeException("Excel export not implemented yet. Requires Apache POI configuration.");
    }

    @Override
    public byte[] exportStockReportToPdf() {
        throw new RuntimeException("PDF export not implemented yet. Requires JasperReports template configuration.");
    }

    @Override
    public byte[] exportStockReportToExcel() {
        throw new RuntimeException("Excel export not implemented yet. Requires Apache POI configuration.");
    }

    @Override
    public byte[] exportProfitReportToPdf(LocalDateTime startDate, LocalDateTime endDate) {
        throw new RuntimeException("PDF export not implemented yet. Requires JasperReports template configuration.");
    }

    @Override
    public byte[] exportProfitReportToExcel(LocalDateTime startDate, LocalDateTime endDate) {
        throw new RuntimeException("Excel export not implemented yet. Requires Apache POI configuration.");
    }
}
