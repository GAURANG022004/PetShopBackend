package com.petshop.service.impl;

import com.petshop.dto.response.InvoiceResponse;
import com.petshop.entity.Invoice;
import com.petshop.entity.InvoiceStatus;
import com.petshop.entity.Sale;
import com.petshop.exception.ResourceNotFoundException;
import com.petshop.mapper.InvoiceMapper;
import com.petshop.repository.InvoiceRepository;
import com.petshop.repository.SaleRepository;
import com.petshop.service.InvoiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.Year;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final SaleRepository saleRepository;
    private final InvoiceMapper invoiceMapper;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository,
                             SaleRepository saleRepository,
                             InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.saleRepository = saleRepository;
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    public InvoiceResponse generateInvoice(Long saleId) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale", saleId));

        if (invoiceRepository.findBySaleId(saleId).isPresent()) {
            throw new ResourceNotFoundException("Invoice already exists for sale", saleId);
        }

        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(generateInvoiceNumber());
        invoice.setSale(sale);
        invoice.setTotalAmount(sale.getTotalAmount());
        invoice.setGeneratedAt(LocalDateTime.now());
        invoice.setStatus(InvoiceStatus.GENERATED);

        invoice = invoiceRepository.save(invoice);
        return invoiceMapper.toResponse(invoice);
    }

    @Override
    public InvoiceResponse getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", id));
        return invoiceMapper.toResponse(invoice);
    }

    @Override
    public InvoiceResponse getInvoiceBySaleId(Long saleId) {
        Invoice invoice = invoiceRepository.findBySaleId(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice for sale", saleId));
        return invoiceMapper.toResponse(invoice);
    }

    @Override
    public byte[] downloadInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", id));

        if (invoice.getPdfPath() == null) {
            throw new ResourceNotFoundException("PDF file not generated for invoice", id);
        }

        try {
            Path path = Paths.get(invoice.getPdfPath());
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read PDF file", e);
        }
    }

    private String generateInvoiceNumber() {
        int year = Year.now().getValue();
        String prefix = "INV-" + year + "-";

        long count = invoiceRepository.count() + 1;
        return prefix + String.format("%04d", count);
    }
}
