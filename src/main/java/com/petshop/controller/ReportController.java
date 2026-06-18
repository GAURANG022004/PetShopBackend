package com.petshop.controller;

import com.petshop.dto.response.ProfitReportResponse;
import com.petshop.dto.response.SalesReportResponse;
import com.petshop.dto.response.StockReportResponse;
import com.petshop.response.ApiResponse;
import com.petshop.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Reports", description = "APIs for generating reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/sales")
    @Operation(summary = "Get sales report")
    public ResponseEntity<ApiResponse<SalesReportResponse>> getSalesReport(
            @Parameter(description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        SalesReportResponse response = reportService.getSalesReport(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Sales report retrieved successfully", response));
    }

    @GetMapping("/stock")
    @Operation(summary = "Get stock report")
    public ResponseEntity<ApiResponse<List<StockReportResponse>>> getStockReport() {
        List<StockReportResponse> response = reportService.getStockReport();
        return ResponseEntity.ok(ApiResponse.success("Stock report retrieved successfully", response));
    }

    @GetMapping("/profit")
    @Operation(summary = "Get profit report")
    public ResponseEntity<ApiResponse<ProfitReportResponse>> getProfitReport(
            @Parameter(description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        ProfitReportResponse response = reportService.getProfitReport(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Profit report retrieved successfully", response));
    }

    @GetMapping("/sales/pdf")
    @Operation(summary = "Export sales report to PDF")
    public ResponseEntity<byte[]> exportSalesReportToPdf(
            @Parameter(description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        byte[] pdfBytes = reportService.exportSalesReportToPdf(startDate, endDate);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "sales_report.pdf");
        headers.setContentLength(pdfBytes.length);
        
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/sales/excel")
    @Operation(summary = "Export sales report to Excel")
    public ResponseEntity<byte[]> exportSalesReportToExcel(
            @Parameter(description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        byte[] excelBytes = reportService.exportSalesReportToExcel(startDate, endDate);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "sales_report.xlsx");
        headers.setContentLength(excelBytes.length);
        
        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/stock/pdf")
    @Operation(summary = "Export stock report to PDF")
    public ResponseEntity<byte[]> exportStockReportToPdf() {
        byte[] pdfBytes = reportService.exportStockReportToPdf();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "stock_report.pdf");
        headers.setContentLength(pdfBytes.length);
        
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/stock/excel")
    @Operation(summary = "Export stock report to Excel")
    public ResponseEntity<byte[]> exportStockReportToExcel() {
        byte[] excelBytes = reportService.exportStockReportToExcel();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "stock_report.xlsx");
        headers.setContentLength(excelBytes.length);
        
        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/profit/pdf")
    @Operation(summary = "Export profit report to PDF")
    public ResponseEntity<byte[]> exportProfitReportToPdf(
            @Parameter(description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        byte[] pdfBytes = reportService.exportProfitReportToPdf(startDate, endDate);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "profit_report.pdf");
        headers.setContentLength(pdfBytes.length);
        
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/profit/excel")
    @Operation(summary = "Export profit report to Excel")
    public ResponseEntity<byte[]> exportProfitReportToExcel(
            @Parameter(description = "Start date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        byte[] excelBytes = reportService.exportProfitReportToExcel(startDate, endDate);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "profit_report.xlsx");
        headers.setContentLength(excelBytes.length);
        
        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }
}
