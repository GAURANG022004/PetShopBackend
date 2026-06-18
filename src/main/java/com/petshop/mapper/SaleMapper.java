package com.petshop.mapper;

import com.petshop.dto.response.SaleResponse;
import com.petshop.entity.Sale;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SaleMapper {

    private final SaleItemMapper saleItemMapper;

    public SaleMapper(SaleItemMapper saleItemMapper) {
        this.saleItemMapper = saleItemMapper;
    }

    public SaleResponse toResponse(Sale sale) {
        if (sale == null) {
            return null;
        }
        SaleResponse response = new SaleResponse();
        response.setId(sale.getId());
        response.setSaleNumber(sale.getSaleNumber());
        response.setSaleDate(sale.getSaleDate());
        response.setTotalAmount(sale.getTotalAmount());
        response.setTotalItems(sale.getTotalItems());
        response.setStatus(sale.getStatus());
        response.setSaleItems(sale.getSaleItems().stream()
                .map(saleItemMapper::toResponse)
                .collect(Collectors.toList()));
        response.setCreatedAt(sale.getCreatedAt());
        response.setUpdatedAt(sale.getUpdatedAt());
        return response;
    }
}
