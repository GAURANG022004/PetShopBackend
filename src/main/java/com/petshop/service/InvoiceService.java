package com.petshop.service;

import com.petshop.dto.response.InvoiceResponse;


public interface InvoiceService {

    InvoiceResponse generateInvoice(Long saleId);

    InvoiceResponse getInvoiceById(Long id);

    InvoiceResponse getInvoiceBySaleId(Long saleId);

    byte[] downloadInvoice(Long id);
}
