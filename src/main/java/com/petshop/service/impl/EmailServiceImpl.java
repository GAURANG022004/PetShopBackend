package com.petshop.service.impl;

import com.petshop.entity.Invoice;
import com.petshop.exception.ResourceNotFoundException;
import com.petshop.repository.InvoiceRepository;
import com.petshop.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private final InvoiceRepository invoiceRepository;

    public EmailServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public void sendInvoiceEmail(Long invoiceId, String toEmail) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", invoiceId));

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Invoice - " + invoice.getInvoiceNumber());
            helper.setText("Please find attached the invoice for your purchase.");

            if (invoice.getPdfPath() != null) {
                byte[] pdfBytes = Files.readAllBytes(Paths.get(invoice.getPdfPath()));
                DataSource dataSource = new ByteArrayDataSource(pdfBytes, "application/pdf");
                helper.addAttachment("invoice_" + invoice.getInvoiceNumber() + ".pdf", dataSource);
            }

            mailSender.send(message);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
