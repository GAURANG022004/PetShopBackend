package com.petshop.dto.response;

import com.petshop.entity.SaleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponse {

    private Long id;
    private String saleNumber;
    private LocalDateTime saleDate;
    private BigDecimal totalAmount;
    private Integer totalItems;
    private SaleStatus status;
    private List<SaleItemResponse> saleItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
