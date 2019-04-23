package com.fredastone.pandacore.models;

import com.fredastone.pandacore.entity.Lease;
import com.fredastone.pandacore.entity.Sale;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeaseSale {

	private Sale sale;
	private Lease lease;
	
	
}
