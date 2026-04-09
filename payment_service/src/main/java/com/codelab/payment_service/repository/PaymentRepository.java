package com.codelab.payment_service.repository;

import com.codelab.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    Payment findByOrderId(Long orderId);

}
