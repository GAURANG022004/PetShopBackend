package com.petshop.service;

import com.razorpay.RazorpayException;

public interface RazorpayService {
        String createOrder(String amount, String currency, String receiptId) throws RazorpayException;         
}   
