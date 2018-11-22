package com.fredastone.pandacore.pandacore.service;

import org.springframework.http.ResponseEntity;

import com.fredastone.pandacore.pandacore.models.PaymentCompleted;
import com.fredastone.pandacore.pandacore.models.PaymentCompletedResponse;
import com.fredastone.pandacore.pandacore.models.RollbackRequest;
import com.fredastone.pandacore.pandacore.models.RollbackResponse;

public interface IOperationService {

	ResponseEntity<String> getMMFinancialInformation(String meterNumber);

	ResponseEntity<PaymentCompletedResponse> paymentCompleted(PaymentCompleted meterNumber);

	ResponseEntity<RollbackResponse> rollback(RollbackRequest meterNumber);

}
