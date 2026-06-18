package com.petshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "stocks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    @Column(nullable = false)
    private Integer stockIn = 0;

    @Column(nullable = false)
    private Integer stockOut = 0;

    @Column(nullable = false)
    private Integer currentStock = 0;

    @Column(nullable = false)
    private Integer damagedStock = 0;

    @Column(nullable = false)
    private Integer lowStockLimit = 10;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @PrePersist
    @PreUpdate
    protected void updateTimestamp() {
        this.lastUpdated = LocalDateTime.now();
    }

    public void addStockIn(Integer quantity) {
        this.stockIn += quantity;
        this.currentStock += quantity;
        this.lastUpdated = LocalDateTime.now();
    }

    public void addStockOut(Integer quantity) {
        this.stockOut += quantity;
        this.currentStock -= quantity;
        this.lastUpdated = LocalDateTime.now();
    }

    public void addDamagedStock(Integer quantity) {
        this.damagedStock += quantity;
        this.currentStock -= quantity;
        this.lastUpdated = LocalDateTime.now();
    }
}
