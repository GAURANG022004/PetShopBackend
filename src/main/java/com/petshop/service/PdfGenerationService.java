package com.petshop.service;

import com.petshop.entity.Invoice;

import java.io.IOException;

public interface PdfGenerationService {

    String generateInvoicePdf(Invoice invoice) throws IOException;
}
