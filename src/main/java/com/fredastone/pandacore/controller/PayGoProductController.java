package com.fredastone.pandacore.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.constants.PayGoProductStatus;
import com.fredastone.pandacore.entity.LeaseOffer;
import com.fredastone.pandacore.entity.PayGoProduct;
import com.fredastone.pandacore.models.PayGoProductModel;
import com.fredastone.pandacore.service.PayGoProductService;

@RestController
@RequestMapping(path="v1/paygoproduct")
public class PayGoProductController {
	
	private PayGoProductService payGoProductService;
	
	public PayGoProductController(PayGoProductService payGoProductService) {
		this.payGoProductService = payGoProductService;
	}
	
	@RequestMapping(path="stock/add",method = RequestMethod.POST)
	public ResponseEntity<?> stockPayGoProduct(@Valid @RequestBody PayGoProductModel payGoProductModel){
		return ResponseEntity.ok(payGoProductService.addPayGoProduct(payGoProductModel));
	}
	
	@RequestMapping(path="get/{serial}",method = RequestMethod.GET)
	public ResponseEntity<?> getPayGoProduct(@Valid @PathVariable("serial") String tokenSerial) {
		return ResponseEntity.ok(payGoProductService.getPayGoProduct(tokenSerial));
	}
	
	@RequestMapping(path="isavailable/{serial}",method = RequestMethod.GET)
	public ResponseEntity<?> productIsAvailable(@Valid @PathVariable("serial") String tokenSerial){
		return ResponseEntity.ok(payGoProductService.isPayGoPayGoProductAvailable(tokenSerial));
	}

}
