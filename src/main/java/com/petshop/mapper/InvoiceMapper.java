package com.petshop.mapper;

import com.petshop.dto.response.InvoiceResponse;
import com.petshop.entity.Invoice;
import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper {

    public InvoiceResponse toResponse(Invoice invoice) {
        if (invoice == null) {
            return null;
        }
        InvoiceResponse response = new InvoiceResponse();
        response.setId(invoice.getId());
        response.setInvoiceNumber(invoice.getInvoiceNumber());
        response.setSaleId(invoice.getSale() != null ? invoice.getSale().getId() : null);
        response.setSaleNumber(invoice.getSale() != null ? invoice.getSale().getSaleNumber() : null);
        response.setTotalAmount(invoice.getTotalAmount());
        response.setGeneratedAt(invoice.getGeneratedAt());
        response.setStatus(invoice.getStatus());
        response.setPdfPath(invoice.getPdfPath());
        response.setDownloadUrl(invoice.getPdfPath() != null ? "/api/invoices/download/" + invoice.getId() : null);
        response.setCreatedAt(invoice.getCreatedAt());
        response.setUpdatedAt(invoice.getUpdatedAt());
        return response;
    }
}
