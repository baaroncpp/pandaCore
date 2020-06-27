package com.fredastone.pandacore.reportMgt.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ResourceUtils;

import com.fredastone.pandacore.entity.LeasePayment;
import com.fredastone.pandacore.repository.LeasePaymentRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportServiceImp implements ReportService {
	
	private LeasePaymentRepository leasePaymentRepository;
	
	@Autowired
	public ReportServiceImp() {
		
	}

	@Override
	public void dailyPayments() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
		
		List<LeasePayment> lp = new ArrayList<>();		
		String path = "/root/Desktop/reports";
		File file  = ResourceUtils.getFile("classpath:dailypaymentsreport.jrxml");
		
		Map<String, Object> parameters = new HashMap<>();
		
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lp);		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
		
		if(reportFormat.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint, path+ "\\payments.html");
		}
		
        if(reportFormat.equalsIgnoreCase("pdf")) {
    	    JasperExportManager.exportReportToHtmlFile(jasperPrint, path+ "\\payments.pdf");
	    }
		
		
		return "";
	}

}
