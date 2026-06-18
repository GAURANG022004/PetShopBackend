package com.petshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesReportResponse {

    private Long totalSales;
    private BigDecimal revenue;
    private Integer totalItemsSold;
}
