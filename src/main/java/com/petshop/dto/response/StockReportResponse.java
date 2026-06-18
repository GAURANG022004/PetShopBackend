package com.petshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockReportResponse {

    private Long productId;
    private String productName;
    private String productCode;
    private Integer currentStock;
    private Boolean isLowStock;
}
