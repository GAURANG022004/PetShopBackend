package com.petshop.service.impl;

import com.petshop.entity.Invoice;
import com.petshop.service.PdfGenerationService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class PdfGenerationServiceImpl implements PdfGenerationService {

    @Override
    public String generateInvoicePdf(Invoice invoice) throws IOException {
        String pdfDirectory = "invoices";
        Path path = Paths.get(pdfDirectory);
        
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        String pdfFileName = "invoice_" + invoice.getInvoiceNumber() + ".pdf";
        String pdfPath = pdfDirectory + File.separator + pdfFileName;

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("invoiceNumber", invoice.getInvoiceNumber());
        parameters.put("saleNumber", invoice.getSale() != null ? invoice.getSale().getSaleNumber() : "");
        parameters.put("totalAmount", invoice.getTotalAmount());
        parameters.put("generatedAt", invoice.getGeneratedAt());
        parameters.put("customerName", "Customer");

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
                invoice.getSale() != null ? invoice.getSale().getSaleItems() : java.util.Collections.emptyList()
        );

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/reports/invoice_template.jrxml")
            );

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            try (FileOutputStream outputStream = new FileOutputStream(pdfPath)) {
                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            }

            return pdfPath;
        } catch (JRException e) {
            throw new IOException("Failed to generate PDF", e);
        }
    }
}
