package com.petshop.service;

import org.springframework.stereotype.Service;

import com.razorpay.RazorpayException;

@Service
public interface RazorpayService {
        String createOrder(String amount, String currency, String receiptId) throws RazorpayException;         
}   
