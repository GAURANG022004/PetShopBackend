package com.petshop.controller;

import com.petshop.dto.request.SaleRequest;
import com.petshop.dto.response.SaleResponse;
import com.petshop.response.ApiResponse;
import com.petshop.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@Tag(name = "Sales Management", description = "APIs for managing sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    @Operation(summary = "Create a new sale")
    public ResponseEntity<ApiResponse<SaleResponse>> createSale(@Valid @RequestBody SaleRequest request) {
        SaleResponse response = saleService.createSale(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Sale created successfully", response));
    }

    @GetMapping
    @Operation(summary = "Get all sales")
    public ResponseEntity<ApiResponse<List<SaleResponse>>> getAllSales() {
        List<SaleResponse> responses = saleService.getAllSales();
        return ResponseEntity.ok(ApiResponse.success("Sales retrieved successfully", responses));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get sale by ID")
    public ResponseEntity<ApiResponse<SaleResponse>> getSaleById(@PathVariable Long id) {
        SaleResponse response = saleService.getSaleById(id);
        return ResponseEntity.ok(ApiResponse.success("Sale retrieved successfully", response));
    }

    @GetMapping("/number/{saleNumber}")
    @Operation(summary = "Get sale by sale number")
    public ResponseEntity<ApiResponse<SaleResponse>> getSaleBySaleNumber(@PathVariable String saleNumber) {
        SaleResponse response = saleService.getSaleBySaleNumber(saleNumber);
        return ResponseEntity.ok(ApiResponse.success("Sale retrieved successfully", response));
    }

    @GetMapping("/today")
    @Operation(summary = "Get today's sales")
    public ResponseEntity<ApiResponse<List<SaleResponse>>> getTodaySales() {
        List<SaleResponse> responses = saleService.getTodaySales();
        return ResponseEntity.ok(ApiResponse.success("Today's sales retrieved successfully", responses));
    }

    @GetMapping("/monthly")
    @Operation(summary = "Get monthly sales")
    public ResponseEntity<ApiResponse<List<SaleResponse>>> getMonthlySales() {
        List<SaleResponse> responses = saleService.getMonthlySales();
        return ResponseEntity.ok(ApiResponse.success("Monthly sales retrieved successfully", responses));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete sale")
    public ResponseEntity<ApiResponse<Void>> deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.ok(ApiResponse.success("Sale deleted successfully"));
    }
}
