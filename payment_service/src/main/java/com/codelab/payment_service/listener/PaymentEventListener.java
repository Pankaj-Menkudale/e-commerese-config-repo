package com.codelab.payment_service.listener;


import com.codelab.payment_service.dto.ProductUpdatedEvent;
import com.codelab.payment_service.dto.PaymentProcessedEvent;
import com.codelab.payment_service.dto.OrderCompensationEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventListener {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentEventListener(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "product-updated-events", groupId = "payment-group",
            containerFactory = "paymentKafkaListenerContainerFactory")
    public void handleProductUpdated(ProductUpdatedEvent event) {
        // 1️⃣ Simulate payment processing
        boolean paymentSuccess = processPayment(event);

        // 2️⃣ Publish success or failure
        if(paymentSuccess) {
            PaymentProcessedEvent processedEvent = new PaymentProcessedEvent(event.getOrderId(), true);
            kafkaTemplate.send("payment-events", processedEvent);
        } else {
            OrderCompensationEvent compensationEvent = new OrderCompensationEvent(event.getOrderId());
            kafkaTemplate.send("payment-compensate-events", compensationEvent);
        }
    }

    private boolean processPayment(ProductUpdatedEvent event) {
        // Simulate payment logic (real would call payment gateway)
        return event.getAmount() != null && event.getAmount() > 0;
    }
}