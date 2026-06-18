package com.petshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.petshop.service.RazorpayService;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/api/razorpay")
public class RazorpayController {

    @Autowired
    private final RazorpayService razorpayService;

    public RazorpayController(RazorpayService razorpayService) {
        this.razorpayService = razorpayService;
    }

    @PostMapping("/create-order")
    public String createOrder(@RequestParam String amount, @RequestParam String currency, @RequestParam String receiptId) throws RazorpayException{
        return razorpayService.createOrder(amount, currency, receiptId);
    } 
    
   
    
}
