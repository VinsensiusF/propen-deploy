package com.unimate.unimate.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unimate.unimate.dto.SalesPercentageDTO;
import com.unimate.unimate.entity.Account;
import com.unimate.unimate.entity.Kelas;
import com.unimate.unimate.entity.Payment;
import java.util.UUID;

public interface PaymentService {
    void savePayment(Payment payment);
     List<Payment> getAllPaymentByStudent(Account siswa);
     Payment getPayment(UUID uuid);
     Payment updatePayment(Payment payment, String method, long gross);
     List<Payment> findPaymentToday();
     SalesPercentageDTO findGPaymentToday();
     SalesPercentageDTO findG2PaymentToday();
     SalesPercentageDTO findGPaymentLast7Days();
     SalesPercentageDTO findG2PaymentLast7Days();
     SalesPercentageDTO findGPaymentLast30Days();
     SalesPercentageDTO findG2PaymentLast30Days(); 
     List<Payment> findPaymentsLast7Days();
     List<Payment> findPaymentsLast30Days();
      List<Payment> findBySiswaIdAndCourseId(Long accountId, Long courseId);
}
