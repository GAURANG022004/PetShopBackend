package com.petshop.dto.response;

import com.petshop.entity.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponse {

    private Long id;
    private String invoiceNumber;
    private Long saleId;
    private String saleNumber;
    private BigDecimal totalAmount;
    private LocalDateTime generatedAt;
    private InvoiceStatus status;
    private String pdfPath;
    private String downloadUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
