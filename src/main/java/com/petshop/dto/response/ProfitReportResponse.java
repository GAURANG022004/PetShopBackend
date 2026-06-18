package com.petshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfitReportResponse {

    private BigDecimal revenue;
    private BigDecimal cost;
    private BigDecimal profit;
    private Double profitPercentage;
}
