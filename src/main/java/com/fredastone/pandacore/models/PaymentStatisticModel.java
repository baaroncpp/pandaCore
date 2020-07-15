package com.fredastone.pandacore.models;

import java.util.List;
import lombok.Data;

@Data
public class PaymentStatisticModel {	
	private Long dailyPayments;
	private List<KeyValueModel> weeklyPayments;
	private List<KeyValueModel> monthlyPayments;	
}
