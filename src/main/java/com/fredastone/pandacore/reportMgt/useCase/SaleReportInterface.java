package com.fredastone.pandacore.reportMgt.useCase;

import java.util.Date;
import java.util.List;

import com.fredastone.pandacore.models.KeyValueModel;
import com.fredastone.pandacore.models.PaymentStatisticModel;
import com.fredastone.pandacore.models.SaleStatisticsModel;
import com.fredastone.pandacore.models.TokenRevenue;

public interface SaleReportInterface {
	
	SaleStatisticsModel getSaleStatistics(String leaseid);
	PaymentStatisticModel getPaymentStatisticModel(String userId);
	List<TokenRevenue> tokenRevenues(Date date, String revenueType);
	List<KeyValueModel> salesFinanceMetrics(Date date);

}
