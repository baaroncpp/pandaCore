package com.fredastone.pandacore.pandacore.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fredastone.pandacore.pandacore.entity.AccountFinancialInformation;
import com.fredastone.pandacore.pandacore.entity.FeesCollected;
import com.fredastone.pandacore.pandacore.entity.UserAccount;
import com.fredastone.pandacore.pandacore.models.PaymentCompleted;
import com.fredastone.pandacore.pandacore.models.PaymentCompletedResponse;
import com.fredastone.pandacore.pandacore.models.RollbackRequest;
import com.fredastone.pandacore.pandacore.models.RollbackResponse;
import com.fredastone.pandacore.pandacore.repository.IAccountsRepository;
import com.fredastone.pandacore.pandacore.repository.IOperationalRepository;

@Service
public class OperationService implements IOperationService {

	@Autowired
	private IAccountsRepository accountRepository;

	@Autowired
	private IOperationalRepository operationRepository;

	private static final String EMPTY_JSON_OBJ = "{}";

	@Override
	public ResponseEntity<String> getMMFinancialInformation(String meterNumber) {

		UserAccount acct = accountRepository.getTransAccountByMeterNo(meterNumber);

		if (acct == null)
			return (new ResponseEntity<String>(EMPTY_JSON_OBJ, HttpStatus.NO_CONTENT));

		if (acct.getIsEnabled() == 0 || acct.getIsTransactional() == 0) {
			// Not allowed to do any transctions
			return (new ResponseEntity<String>(EMPTY_JSON_OBJ, HttpStatus.FORBIDDEN));
		}

		final List<AccountFinancialInformation> ahi = operationRepository.getAccountFinancialInformation(meterNumber);

		List<AccountFinancialInformation> billable = new ArrayList<>();

		for (AccountFinancialInformation k : ahi) {
			if (k.getIsTransactional() == 1 & k.getActiveAccount() == 1) {
				billable.add(k);
			}
		}

		String result = null;
		if (billable.isEmpty()) {
			result = buildMMGetFinancialInformationResp(acct);
			return (new ResponseEntity<String>(result, HttpStatus.OK));
		}

		result = buildMMGetFinancialInformationResp(billable);

		return (new ResponseEntity<String>(result, HttpStatus.OK));

	}

	private <T> String buildMMGetFinancialInformationResp(T input) {

		JSONObject obj = new JSONObject();

		if (input instanceof UserAccount) {
			UserAccount ua = (UserAccount) input;

			obj.put("firstName", ua.getFirstName());
			obj.put("lastName", ua.getLastName());
			obj.put("otherNames", ua.getOtherNames());
			obj.put("meterNumber", ua.getMeterNumber());
			obj.put("mobileNumber", ua.getMobileNumber());
			obj.put("fees", new JSONArray());
			obj.put("balance", 0);
		} else {

			JSONArray arr = new JSONArray();
			float totalFees = 0;

			@SuppressWarnings("unchecked")
			List<AccountFinancialInformation> billable = (List<AccountFinancialInformation>) input;

			for (AccountFinancialInformation b : billable) {
				if (b.getFeesCleared() == 0) {
					totalFees += b.getBalanceDue();

					JSONObject objk = new JSONObject();
					objk.put("description", b.getFeesDescription());
					objk.put("amount", b.getBalanceDue());

					arr.put(objk);

				}
			}

			obj.put("firstName", billable.get(0).getFirstName());
			obj.put("lastName", billable.get(0).getLastName());
			obj.put("otherNames", billable.get(0).getOtherNames());
			obj.put("meterNumber", billable.get(0).getMeterNumber());
			obj.put("mobileNumber", billable.get(0).getMobileNumber());
			obj.put("fees", arr);
			obj.put("balance", totalFees);

		}

		return obj.toString();

	}

	@Override
	public ResponseEntity<PaymentCompletedResponse> paymentCompleted(PaymentCompleted payment) {
		
		System.out.println(payment.toString());
		
		System.out.println("Getting token at core");
		final String  token = operationRepository.getPaymentToken(payment.getAmount());

		final PaymentCompletedResponse resp = new PaymentCompletedResponse();
		resp.setMessage("Processed Transaction Successfully");
		resp.setMeterNumber(payment.getMeterNumber());
		resp.setNewBalance(payment.getAmount());
		resp.setPaymentToken(token);
		resp.setScheduledTransId(payment.getScheduledTransactionId());
		resp.setTransactionId(payment.getTransactionId());
		
		final FeesCollected collected = new FeesCollected();
		collected.setAmount(payment.getAmount());
		collected.setMeterNumber(payment.getMeterNumber());
		collected.setToken(resp.getPaymentToken());

		operationRepository.createNewCollectedFee(collected);
		
		return new ResponseEntity<PaymentCompletedResponse>(resp, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<RollbackResponse> rollback(RollbackRequest rollback) {

		final RollbackResponse response = new RollbackResponse();
		response.setMeterNumber(rollback.getMeterNumber());
		response.setOldBalance("300");
		response.setOldToken("test-49989494399493");
		response.setPayingMsisdn("");
		response.setStatus("SUCCESSFUL");

		return new ResponseEntity<RollbackResponse>(response, HttpStatus.OK);
	}

}
