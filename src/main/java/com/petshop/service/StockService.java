package com.petshop.service;

import com.petshop.dto.request.DamagedStockRequest;
import com.petshop.dto.request.StockInRequest;
import com.petshop.dto.request.StockOutRequest;
import com.petshop.dto.response.StockResponse;

import java.util.List;

public interface StockService {

    StockResponse addStockIn(StockInRequest request);

    StockResponse addStockOut(StockOutRequest request);

    StockResponse addDamagedStock(DamagedStockRequest request);

    List<StockResponse> getAllStocks();

    List<StockResponse> getLowStockProducts();

    StockResponse getStockByProductId(Long productId);
}
