package com.petshop.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private Long totalProducts;
    private Long totalCategories;
    private Integer totalStockQuantity;
    private Long lowStockProducts;
    private Long todaySales;
    private BigDecimal monthlyRevenue;
    private BigDecimal totalRevenue;
    private List<SaleResponse> recentSales;
}
