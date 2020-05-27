package com.fredastone.pandacore.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.fredastone.security.JwtTokenUtil;

@RestController
@RequestMapping(path="v1/paygoproduct")
public class PayGoProductController {
	
	@Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	private PayGoProductService payGoProductService;
	
	public PayGoProductController(PayGoProductService payGoProductService) {
		this.payGoProductService = payGoProductService;
	}
	
	@RequestMapping(path="stock/add",method = RequestMethod.POST)
	public ResponseEntity<?> stockPayGoProduct(HttpServletRequest request, @Valid @RequestBody PayGoProductModel payGoProductModel){
		String id = jwtTokenUtil.getUserId(request.getHeader(tokenHeader).substring(7));
		return ResponseEntity.ok(payGoProductService.addPayGoProduct(id, payGoProductModel));
	}
	
	@RequestMapping(path="get/{serial}",method = RequestMethod.GET)
	public ResponseEntity<?> getPayGoProduct(@Valid @PathVariable("serial") String tokenSerial) {
		return ResponseEntity.ok(payGoProductService.getPayGoProduct(tokenSerial));
	}
	
	@RequestMapping(path="isavailable/{serial}",method = RequestMethod.GET)
	public ResponseEntity<?> productIsAvailable(@Valid @PathVariable("serial") String tokenSerial){
		return ResponseEntity.ok(payGoProductService.isPayGoPayGoProductAvailable(tokenSerial));
	}
	
	@RequestMapping(path="paygo/stock",method = RequestMethod.GET)
	public ResponseEntity<?> getStockValue(){
		return ResponseEntity.ok(payGoProductService.getPayGoStockValues());
	}

}
