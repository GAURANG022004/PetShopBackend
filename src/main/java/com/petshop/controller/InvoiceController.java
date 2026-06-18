package com.petshop.controller;

import com.petshop.dto.response.InvoiceResponse;
import com.petshop.response.ApiResponse;
import com.petshop.service.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
@Tag(name = "Invoice Management", description = "APIs for managing invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/generate/{saleId}")
    @Operation(summary = "Generate invoice for a sale")
    public ResponseEntity<ApiResponse<InvoiceResponse>> generateInvoice(@PathVariable Long saleId) {
        InvoiceResponse response = invoiceService.generateInvoice(saleId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Invoice generated successfully", response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get invoice by ID")
    public ResponseEntity<ApiResponse<InvoiceResponse>> getInvoiceById(@PathVariable Long id) {
        InvoiceResponse response = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(ApiResponse.success("Invoice retrieved successfully", response));
    }

    @GetMapping("/sale/{saleId}")
    @Operation(summary = "Get invoice by sale ID")
    public ResponseEntity<ApiResponse<InvoiceResponse>> getInvoiceBySaleId(@PathVariable Long saleId) {
        InvoiceResponse response = invoiceService.getInvoiceBySaleId(saleId);
        return ResponseEntity.ok(ApiResponse.success("Invoice retrieved successfully", response));
    }

    @GetMapping("/download/{id}")
    @Operation(summary = "Download invoice PDF")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable Long id) {
        byte[] pdfBytes = invoiceService.downloadInvoice(id);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "invoice_" + id + ".pdf");
        headers.setContentLength(pdfBytes.length);
        
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
