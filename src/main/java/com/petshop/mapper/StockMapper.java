package com.petshop.mapper;

import com.petshop.dto.response.StockResponse;
import com.petshop.entity.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {

    public StockResponse toResponse(Stock stock) {
        if (stock == null) {
            return null;
        }
        StockResponse response = new StockResponse();
        response.setId(stock.getId());
        response.setProductId(stock.getProduct() != null ? stock.getProduct().getId() : null);
        response.setProductName(stock.getProduct() != null ? stock.getProduct().getProductName() : null);
        response.setProductCode(stock.getProduct() != null ? stock.getProduct().getProductCode() : null);
        response.setStockIn(stock.getStockIn());
        response.setStockOut(stock.getStockOut());
        response.setCurrentStock(stock.getCurrentStock());
        response.setDamagedStock(stock.getDamagedStock());
        response.setLowStockLimit(stock.getLowStockLimit());
        response.setLastUpdated(stock.getLastUpdated());
        return response;
    }
}
