package com.petshop.service;

import com.petshop.dto.request.SaleRequest;
import com.petshop.dto.response.SaleResponse;

import java.util.List;

public interface SaleService {

    SaleResponse createSale(SaleRequest request);

    SaleResponse getSaleById(Long id);

    List<SaleResponse> getAllSales();

    void deleteSale(Long id);

    SaleResponse getSaleBySaleNumber(String saleNumber);

    List<SaleResponse> getTodaySales();

    List<SaleResponse> getMonthlySales();
}
