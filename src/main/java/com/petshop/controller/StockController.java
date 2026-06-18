package com.petshop.controller;

import com.petshop.dto.request.DamagedStockRequest;
import com.petshop.dto.request.StockInRequest;
import com.petshop.dto.request.StockOutRequest;
import com.petshop.dto.response.StockResponse;
import com.petshop.response.ApiResponse;
import com.petshop.service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/in")
    public ResponseEntity<ApiResponse<StockResponse>> addStockIn(@Valid @RequestBody StockInRequest request) {
        StockResponse response = stockService.addStockIn(request);
        return ResponseEntity.ok(ApiResponse.success("Stock added successfully", response));
    }

    @PostMapping("/out")
    public ResponseEntity<ApiResponse<StockResponse>> addStockOut(@Valid @RequestBody StockOutRequest request) {
        StockResponse response = stockService.addStockOut(request);
        return ResponseEntity.ok(ApiResponse.success("Stock removed successfully", response));
    }

    @PostMapping("/damaged")
    public ResponseEntity<ApiResponse<StockResponse>> addDamagedStock(@Valid @RequestBody DamagedStockRequest request) {
        StockResponse response = stockService.addDamagedStock(request);
        return ResponseEntity.ok(ApiResponse.success("Damaged stock recorded successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StockResponse>>> getAllStocks() {
        List<StockResponse> responses = stockService.getAllStocks();
        return ResponseEntity.ok(ApiResponse.success("Stocks retrieved successfully", responses));
    }

    @GetMapping("/low")
    public ResponseEntity<ApiResponse<List<StockResponse>>> getLowStockProducts() {
        List<StockResponse> responses = stockService.getLowStockProducts();
        return ResponseEntity.ok(ApiResponse.success("Low stock products retrieved successfully", responses));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<StockResponse>> getStockByProductId(@PathVariable Long productId) {
        StockResponse response = stockService.getStockByProductId(productId);
        return ResponseEntity.ok(ApiResponse.success("Stock retrieved successfully", response));
    }
}
