package com.fredastone.pandacore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.reportMgt.useCase.SaleReportInterface;

@RestController
@RequestMapping("v1/reports")
public class ReportsController {
	
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

}
