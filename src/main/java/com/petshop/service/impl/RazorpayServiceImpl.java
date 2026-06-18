package com.petshop.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import com.petshop.service.RazorpayService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

public class RazorpayServiceImpl implements RazorpayService {

     
        @Value("${razorpay.key}")
        private String razorpaykey;

        @Value("${razorpay.secret}")
        private String razorpaysecret;
    
    
    @Override
    public String createOrder(String amount,String currency, String receiptId) throws RazorpayException {

        RazorpayClient client = new RazorpayClient(razorpaykey, razorpaysecret);

        String amoountInPaise = String.valueOf(Integer.parseInt(amount) * 100);

        String currencyCode = currency != null ? currency  : "INR";

        String receipt = receiptId != null ? receiptId: "Default_receipt_id";

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amoountInPaise);
        orderRequest.put("currency", currencyCode);
        orderRequest.put("receiptId", receipt);


        Order  order = client.Orders.create(orderRequest);
        return order.toString();
        

    }
}
