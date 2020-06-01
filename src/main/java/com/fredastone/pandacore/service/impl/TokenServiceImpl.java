package com.fredastone.pandacore.service.impl;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fredastone.pandacore.entity.Token;
import com.fredastone.pandacore.entity.VCustomerFinanceInfo;
import com.fredastone.pandacore.exception.ItemNotFoundException;
import com.fredastone.pandacore.models.BuyToken;
import com.fredastone.pandacore.repository.BuyTokenRepository;
import com.fredastone.pandacore.repository.CustomerFinanceInfoRepository;
import com.fredastone.pandacore.repository.TokenRepository;
import com.fredastone.pandacore.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService{
	

	private BuyTokenRepository buyTokenRepo;
	private CustomerFinanceInfoRepository customerFinanceDao;
	private TokenRepository tokenRepository;
	
	@Autowired
	public TokenServiceImpl(TokenRepository tokenRepository, CustomerFinanceInfoRepository customerfinanceDao, BuyTokenRepository buyTokenRepo) {
		// TODO Auto-generated constructor stub

		this.customerFinanceDao = customerfinanceDao;
		this.buyTokenRepo = buyTokenRepo;
		this.tokenRepository = tokenRepository;
	}

	@Override
	public Token createToken(String customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Token getToken(String tokenId) {
		
		return null;
	}

	@Override
	public Token getTokenByPaymentReference(String paymentReference) {
		// TODO Auto-generated method stub
		Optional<Token> token = tokenRepository.findByleasepaymentid(paymentReference);
		if(!token.isPresent()) {
			throw new ItemNotFoundException(paymentReference);
		}
		
		return token.get();
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
		if(!info.isPresent()) {
			throw new RuntimeException("No Token Payments made for this device");
		}
		
		return info.get(); 
	}

}
