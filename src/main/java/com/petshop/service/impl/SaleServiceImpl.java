package com.petshop.service.impl;

import com.petshop.dto.request.SaleItemRequest;
import com.petshop.dto.request.SaleRequest;
import com.petshop.dto.response.SaleResponse;
import com.petshop.entity.Product;
import com.petshop.entity.Sale;
import com.petshop.entity.SaleItem;
import com.petshop.entity.SaleStatus;
import com.petshop.entity.Stock;
import com.petshop.exception.InsufficientStockException;
import com.petshop.exception.ResourceNotFoundException;
import com.petshop.mapper.SaleMapper;
import com.petshop.repository.ProductRepository;
import com.petshop.repository.SaleItemRepository;
import com.petshop.repository.SaleRepository;
import com.petshop.repository.StockRepository;
import com.petshop.service.SaleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItem ;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final SaleMapper saleMapper;

    public SaleServiceImpl(SaleRepository saleRepository,
                           SaleItemRepository saleItemRepository,
                           ProductRepository productRepository,
                           StockRepository stockRepository,
                           SaleMapper saleMapper) {
        this.saleRepository = saleRepository;
        this.saleItemRepository = saleItemRepository;
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
        this.saleMapper = saleMapper;
    }

    @Override
    public SaleResponse createSale(SaleRequest request) {
        Sale sale = new Sale();
        sale.setSaleNumber(generateSaleNumber());
        sale.setSaleDate(LocalDateTime.now());
        sale.setStatus(SaleStatus.COMPLETED);

        BigDecimal totalAmount = BigDecimal.ZERO;
        AtomicInteger totalItems = new AtomicInteger(0);

        for (SaleItemRequest itemRequest : request.getSaleItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", itemRequest.getProductId()));

            Stock stock = stockRepository.findByProductId(product.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Stock for product", product.getId()));

            if (stock.getCurrentStock() < itemRequest.getQuantity()) {
                throw new InsufficientStockException(product.getProductName(), itemRequest.getQuantity(), stock.getCurrentStock());
            }

            SaleItem saleItem = new SaleItem();
            saleItem.setSale(sale);
            saleItem.setProduct(product);
            saleItem.setQuantity(itemRequest.getQuantity());
            saleItem.setUnitPrice(product.getPrice());
            saleItem.calculateSubtotal();

            sale.addSaleItem(saleItem);

            totalAmount = totalAmount.add(saleItem.getSubtotal());
            totalItems.addAndGet(itemRequest.getQuantity());

            stock.addStockOut(itemRequest.getQuantity());
            stockRepository.save(stock);
        }

        sale.setTotalAmount(totalAmount);
        sale.setTotalItems(totalItems.get());

        sale = saleRepository.save(sale);
        return saleMapper.toResponse(sale);
    }

    @Override
    public SaleResponse getSaleById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale", id));
        return saleMapper.toResponse(sale);
    }

    @Override
    public List<SaleResponse> getAllSales() {
        List<Sale> sales = saleRepository.findAll();
        return sales.stream()
                .map(saleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSale(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale", id));
        saleRepository.delete(sale);
    }

    @Override
    public SaleResponse getSaleBySaleNumber(String saleNumber) {
        Sale sale = saleRepository.findBySaleNumber(saleNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Sale", "saleNumber", saleNumber));
        return saleMapper.toResponse(sale);
    }

    @Override
    public List<SaleResponse> getTodaySales() {
        List<Sale> sales = saleRepository.findTodaySales();
        return sales.stream()
                .map(saleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SaleResponse> getMonthlySales() {
        List<Sale> sales = saleRepository.findMonthlySales();
        return sales.stream()
                .map(saleMapper::toResponse)
                .collect(Collectors.toList());
    }

    private String generateSaleNumber() {
        int year = Year.now().getValue();
        String prefix = "SAL-" + year + "-";

        long count = saleRepository.count() + 1;
        return prefix + String.format("%04d", count);
    }
}
