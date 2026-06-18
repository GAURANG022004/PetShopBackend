package com.petshop.service;

public interface EmailService {

    void sendInvoiceEmail(Long invoiceId, String toEmail);
}
