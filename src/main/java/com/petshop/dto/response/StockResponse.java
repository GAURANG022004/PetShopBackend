package com.petshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockResponse {

    private Long id;
    private Long productId;
    private String productName;
    private String productCode;
    private Integer stockIn;
    private Integer stockOut;
    private Integer currentStock;
    private Integer damagedStock;
    private Integer lowStockLimit;
    private LocalDateTime lastUpdated;
}
