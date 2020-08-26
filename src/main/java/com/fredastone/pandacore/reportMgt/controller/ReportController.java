package com.fredastone.pandacore.reportMgt.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fredastone.pandacore.reportMgt.useCase.SaleReportInterface;

@RestController
@RequestMapping("v1/reports")
public class ReportController {
	
	private SaleReportInterface saleReport;
	
	@Autowired
	public ReportController(SaleReportInterface saleReport) {
		this.saleReport = saleReport;
	}
	


}
