package com.fredastone.pandacore.reportMgt.service;

import java.io.FileNotFoundException;

import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;

@Service
public interface ReportService {
	
	void dailyPayments();
	String exportReport(String reportFormat) throws FileNotFoundException, JRException;
}
