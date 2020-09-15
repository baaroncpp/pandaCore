package com.fredastone.pandacore.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.reportMgt.useCase.SaleReportInterface;
import com.fredastone.security.JwtTokenUtil;

@RestController
@RequestMapping("v1/reports")
public class ReportsController {
	
	@Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

	
	private SaleReportInterface saleReport;
	
	@Autowired
	public ReportsController(SaleReportInterface saleReport) {
		this.saleReport = saleReport;
	}
	
    @RequestMapping(path="get",params = {"direction", "page","size" },method = RequestMethod.GET)
    public ResponseEntity<?> getReportLists(@RequestParam("direction") String direction,@RequestParam("page") int page,@RequestParam("size") int size) {
        return ResponseEntity.ok("Greetings from admin protected method!");
    }
    
    @RequestMapping(path="salestatistics/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getSaleStatics(@PathVariable("id") String id ){
    	return ResponseEntity.ok(saleReport.getSaleStatistics(id));
    }
    
    @RequestMapping(path="paymentstatistics",method = RequestMethod.GET)
    public ResponseEntity<?> getPaymentStatistics(HttpServletRequest request){
    	String id = jwtTokenUtil.getUserId(request.getHeader(tokenHeader).substring(7));
    	
    	return ResponseEntity.ok(saleReport.getPaymentStatisticModel(id));
    }
    
    @RequestMapping(path="tokenrevenue",method = RequestMethod.GET)
    public ResponseEntity<?> getTokenRevenue() {
        return ResponseEntity.ok(saleReport.tokenRevenues(new Date(), "token_revenue"));
    }
    
    @RequestMapping(path="salesfinancemetrics",method = RequestMethod.GET)
    public ResponseEntity<?> getSalesFinanceMetrics() {
        return ResponseEntity.ok(saleReport.salesFinanceMetrics(new Date()));
    }

}
