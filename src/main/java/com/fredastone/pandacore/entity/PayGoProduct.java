package com.fredastone.pandacore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fredastone.pandacore.constants.PayGoProductStatus;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter @Getter
@Entity
@Table(name = "t_paygo_product",schema="panda_core")
public class PayGoProduct {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	private String id;

	@Column(name = "product_serialtoken", unique = true, nullable = false)
	private String tokenSerialNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "leaseofferid", nullable = false)
	private LeaseOffer leaseOffer;
	
	@Column(name = "productstatus", nullable = false)
	private PayGoProductStatus payGoProductStatus;
	
}
