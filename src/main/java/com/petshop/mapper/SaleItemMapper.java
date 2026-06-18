package com.petshop.mapper;

import com.petshop.dto.response.SaleItemResponse;
import com.petshop.entity.SaleItem;
import org.springframework.stereotype.Component;

@Component
public class SaleItemMapper {

    public SaleItemResponse toResponse(SaleItem saleItem) {
        if (saleItem == null) {
            return null;
        }
        SaleItemResponse response = new SaleItemResponse();
        response.setId(saleItem.getId());
        response.setProductId(saleItem.getProduct() != null ? saleItem.getProduct().getId() : null);
        response.setProductName(saleItem.getProduct() != null ? saleItem.getProduct().getProductName() : null);
        response.setProductCode(saleItem.getProduct() != null ? saleItem.getProduct().getProductCode() : null);
        response.setQuantity(saleItem.getQuantity());
        response.setUnitPrice(saleItem.getUnitPrice());
        response.setSubtotal(saleItem.getSubtotal());
        response.setCreatedAt(saleItem.getCreatedAt());
        return response;
    }
}
