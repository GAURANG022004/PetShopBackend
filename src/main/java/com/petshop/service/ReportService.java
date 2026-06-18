package com.petshop.service;

import com.petshop.dto.response.ProfitReportResponse;
import com.petshop.dto.response.SalesReportResponse;
import com.petshop.dto.response.StockReportResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {

    SalesReportResponse getSalesReport(LocalDateTime startDate, LocalDateTime endDate);

    List<StockReportResponse> getStockReport();

    ProfitReportResponse getProfitReport(LocalDateTime startDate, LocalDateTime endDate);

    byte[] exportSalesReportToPdf(LocalDateTime startDate, LocalDateTime endDate);

    byte[] exportSalesReportToExcel(LocalDateTime startDate, LocalDateTime endDate);

    byte[] exportStockReportToPdf();

    byte[] exportStockReportToExcel();

    byte[] exportProfitReportToPdf(LocalDateTime startDate, LocalDateTime endDate);

    byte[] exportProfitReportToExcel(LocalDateTime startDate, LocalDateTime endDate);
}
