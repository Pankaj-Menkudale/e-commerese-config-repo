package com.codelab.payment_service.serviceImpl;

import com.codelab.payment_service.dto.OrderCompensationEvent;
import com.codelab.payment_service.dto.PaymentProcessedEvent;
import com.codelab.payment_service.dto.PaymentRequest;
import com.codelab.payment_service.entity.Payment;
import com.codelab.payment_service.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor

public class PaymentServiceImpl {


    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final Validator validator;

    @Transactional
    public void processPayment(PaymentRequest dto) {
        // Manual validation
        Set<ConstraintViolation<PaymentRequest>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            kafkaTemplate.send("payment-compensate-events",
                    new OrderCompensationEvent(dto.getOrderId()));
            return;
        }

        Payment payment = new Payment();
        payment.setOrderId(dto.getOrderId());
        payment.setAmount(dto.getAmount());
        payment.setStatus("SUCCESS");
        paymentRepository.save(payment);

        kafkaTemplate.send("payment-events",
                new PaymentProcessedEvent(dto.getOrderId(), true));
    }

    @Transactional
    public void refundPayment(OrderCompensationEvent event) {
        Payment payment = paymentRepository.findByOrderId(event.getOrderId());
        if (payment != null && !"REFUNDED".equals(payment.getStatus())) {
            payment.setStatus("REFUNDED");
            paymentRepository.save(payment);
        }
    }
}
