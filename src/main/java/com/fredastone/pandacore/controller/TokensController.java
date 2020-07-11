package com.fredastone.pandacore.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.entity.Token;
import com.fredastone.pandacore.entity.VCustomerFinanceInfo;
import com.fredastone.pandacore.models.BuyToken;
import com.fredastone.pandacore.service.TokenService;
import com.fredastone.security.JwtTokenUtil;

@RestController
@RequestMapping("v1/tokens")
public class TokensController {
	
	@Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

	private TokenService tokenService;

	@Autowired
	public TokensController(TokenService tokenService) {
		// TODO Auto-generated constructor stub
		this.tokenService = tokenService;
	}

	@RequestMapping(path = "get", params = { "direction", "page", "size" }, method = RequestMethod.GET)
	public ResponseEntity<?> getAllTokens(@RequestParam("direction") String direction, @RequestParam("page") int page,
			@RequestParam("size") int size) {
		return ResponseEntity.ok("Greetings from admin protected method!");
	}

	@RequestMapping(path = "buy", method = RequestMethod.POST)
	public ResponseEntity<?> buyToken(@RequestBody BuyToken request) {

		final Token token = tokenService.buyToken(request);
		
		if(token == null) {
			ResponseEntity.status(500).build();
		}
		
		return ResponseEntity.ok(token);
	}
	
	//test
	@RequestMapping(path = "test", method = RequestMethod.GET)
	public ResponseEntity<?> getoken(){
		BuyToken bt = new BuyToken();
		return ResponseEntity.ok(bt);
	}

	@RequestMapping(path = "financialinfo/{deviceserial}", method = RequestMethod.GET)
	public ResponseEntity<?> buyToken(@PathVariable("deviceserial") String deviceSerial) {

		VCustomerFinanceInfo info = tokenService.getBalanceForTokenPayment(deviceSerial);
		if (info == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(info);
	}
	
	@RequestMapping(path = "paymenttoken/{leasePaymentId}", method = RequestMethod.GET)
	public ResponseEntity<?> getTokenByLeasePayment(@PathVariable("leasePaymentId") String leasePaymentId){
		return ResponseEntity.ok(tokenService.getTokenByPaymentReference(leasePaymentId));
	}
	
	@RequestMapping(path = "payments/{serial}", method = RequestMethod.GET)
	public ResponseEntity<?> getTokenBySerialNumber(@PathVariable("serial") String serial){
		return ResponseEntity.ok(tokenService.getDeviceTokensBySerialNumber(serial));
	}
	
	@RequestMapping(path = "reset/{serial}", method = RequestMethod.GET)
	public ResponseEntity<?> getResetToken(@PathVariable("serial") String serialNumber){
		return ResponseEntity.ok(tokenService.resetDeviceToken(serialNumber));
	}
	
	@RequestMapping(path = "resend/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> resendToken(HttpServletRequest request, @PathVariable("id") String id){
    	String userid = jwtTokenUtil.getUserId(request.getHeader(tokenHeader).substring(7));
    	
    	return ResponseEntity.ok(tokenService.resendToken(id, userid));
    }
	
}
