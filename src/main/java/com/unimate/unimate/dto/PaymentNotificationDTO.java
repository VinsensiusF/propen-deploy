package com.unimate.unimate.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PaymentNotificationDTO {
   
        private List<Map<String, String>> va_numbers;
        private String transaction_time;
        private String transaction_status;
        private String transaction_id;
        private String status_message;
        private String status_code;
        private String signature_key;
        private String settlement_time;
        private String payment_type;
        private List<Map<String, String>> payment_amounts;
        private String order_id;
        private String merchant_id;
        private String gross_amount;
        private String fraud_status;
        private String currency;
    
  
    
    
}
