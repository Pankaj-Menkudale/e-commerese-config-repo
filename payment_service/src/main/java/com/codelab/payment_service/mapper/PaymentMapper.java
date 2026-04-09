package com.codelab.payment_service.mapper;


import com.codelab.payment_service.dto.PaymentRequest;
import com.codelab.payment_service.entity.Payment;

public class PaymentMapper {
    public Payment toEntity(PaymentRequest dto){
        Payment payment = new Payment();
        payment.setOrderId(dto.getOrderId());
        payment.setAmount(dto.getAmount());
        payment.setStatus("PENDING");
        return payment;
    }
}