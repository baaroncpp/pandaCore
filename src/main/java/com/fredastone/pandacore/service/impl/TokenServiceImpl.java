package com.fredastone.pandacore.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fredastone.pandacore.entity.Token;
import com.fredastone.pandacore.entity.VCustomerFinanceInfo;
import com.fredastone.pandacore.models.BuyToken;
import com.fredastone.pandacore.repository.BuyTokenRepository;
import com.fredastone.pandacore.repository.CustomerFinanceInfoRepository;
import com.fredastone.pandacore.service.TokenService;


@Service
public class TokenServiceImpl implements TokenService{
	

	private BuyTokenRepository buyTokenRepo;
	private CustomerFinanceInfoRepository customerFinanceDao;
	
	@Autowired
	public TokenServiceImpl(CustomerFinanceInfoRepository customerfinanceDao,BuyTokenRepository buyTokenRepo) {
		// TODO Auto-generated constructor stub

		this.customerFinanceDao = customerfinanceDao;
		this.buyTokenRepo = buyTokenRepo;
	}

	@Override
	public Token createToken(String customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Token getToken(String tokenId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Token getTokenByPaymentReference(String paymentReference) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Token invalidateToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Token resendToken(String paymentReference) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public Token buyToken(BuyToken token) {
		return buyTokenRepo.commitPayment(token);
	}

	@Override
	public VCustomerFinanceInfo getBalanceForTokenPayment(String deviceserial) {
		
		Optional<VCustomerFinanceInfo> info = customerFinanceDao.findById(deviceserial);
		if(!info.isPresent())
			return null;
		
		return info.get(); 
	}

}
