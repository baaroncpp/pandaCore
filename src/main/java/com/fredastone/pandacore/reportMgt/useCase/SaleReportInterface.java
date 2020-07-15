package com.fredastone.pandacore.reportMgt.useCase;

import com.fredastone.pandacore.models.PaymentStatisticModel;
import com.fredastone.pandacore.models.SaleStatisticsModel;

public interface SaleReportInterface {
	
	SaleStatisticsModel getSaleStatistics(String leaseid);
	PaymentStatisticModel getPaymentStatisticModel(String userId);

}
