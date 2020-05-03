package com.fredastone.pandacore.models;

import com.fredastone.pandacore.entity.LeaseOffer;

import lombok.Data;

@Data
public class StockProduct {
	LeaseOffer leaseOffer;
	Long stockValue;
}
