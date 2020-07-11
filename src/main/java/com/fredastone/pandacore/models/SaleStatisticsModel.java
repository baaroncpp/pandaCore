package com.fredastone.pandacore.models;

import java.util.List;

import com.fredastone.pandacore.entity.CustomerMeta;
import com.fredastone.pandacore.entity.Lease;
import com.fredastone.pandacore.entity.LeasePayment;
import com.fredastone.pandacore.entity.Sale;
import com.fredastone.pandacore.entity.TotalLeasePayments;

import lombok.Data;

@Data
public class SaleStatisticsModel {	
	private Sale sale;
	private CustomerMeta customer;
	private List<LeasePayment> payments;
	private TotalLeasePayments totalLeasePayments;
	private Lease lease;
}
