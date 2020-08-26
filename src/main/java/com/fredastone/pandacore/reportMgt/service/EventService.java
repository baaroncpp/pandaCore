package com.fredastone.pandacore.reportMgt.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.fredastone.pandacore.entity.LeasePayment;
import com.fredastone.pandacore.entity.Sale;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.models.KeyValueModel;
import com.fredastone.pandacore.models.PaymentModel;
import com.fredastone.pandacore.models.TokenRevenue;
import com.fredastone.pandacore.reportMgt.useCase.SaleReportInterface;
import com.fredastone.pandacore.repository.LeasePaymentRepository;
import com.fredastone.pandacore.repository.SaleRepository;
import com.fredastone.pandacore.repository.UserRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class EventService {
	
	private static final String TOKEN_REVENUE = "token_revenue";
	private static final String DEPOSIT_REVENUE = "deposit_revenue";

	@Autowired
	private LeasePaymentRepository leasePaymentRepository;
	
	@Autowired
	private SaleRepository saleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SaleReportInterface saleReport;

	@Scheduled(cron = "0 0 12 * * ?")
	public void importReportEvent() {

	}

	public String exportReport(String reportFormat) throws FileNotFoundException, JRException {

		List<PaymentModel> lp = mapLeasePaymentsToPaymentModel();
		String path = "/root/Desktop/reports";
		File file = ResourceUtils.getFile("classpath:dailypaymentsreport.jrxml");
		
		file.getAbsoluteFile();

		Map<String, Object> parameters = new HashMap<>();

		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lp);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

		if(reportFormat.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "/payments.html");
		}

		if(reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint, path + "/payments.pdf");
		}
		
		if(reportFormat.equalsIgnoreCase("xml")) {
			JasperExportManager.exportReportToXmlFile(jasperPrint, path + "/payments.xml", false);
		}

		return "";
	}

	public List<PaymentModel> mapLeasePaymentsToPaymentModel() {

		List<LeasePayment> lp = (List<LeasePayment>) leasePaymentRepository.findAll();
		List<PaymentModel> pml = new ArrayList<>();

		int a = 0;
		for (LeasePayment object : lp) {
			
			Optional<Sale> sale = saleRepository.findById(object.getLeaseid());
			
			Optional<User> agent = userRepository.findById(sale.get().getAgentid());
			
			Optional<User> customer = userRepository.findById(sale.get().getCustomerid());
 			
			PaymentModel pm = new PaymentModel();

			pm.setAgent(agent.get().getFirstname()+" "+agent.get().getLastname());
			pm.setSerial(sale.get().getScannedserial());
			pm.setMsisdn(object.getPayeemobilenumber());
			pm.setAmount((double) object.getAmount());
			pm.setDate(object.getCreatedon());
			pm.setCustomername(customer.get().getFirstname()+" "+customer.get().getLastname());
			pm.setNumber(Integer.toString(a = a + 1));

			pml.add(pm);
		}
		return pml;
	}
	
	//@Scheduled(fixedRate = 5000) 
	public void reportCurrentTime() throws FileNotFoundException, JRException { 
		//exportReport("pdf");
	  //  System.out.println("Scheduler test "+new Date()); 
	}
	
	
	@Scheduled(fixedRate = 5000)//@Scheduled(cron = "0 30 8 1 * ?")//at 8:30 every first day of the month
	public void monthlyReport() throws FileNotFoundException, JRException {
		
		float depositTotalAmount = 0;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, -1);
		
		//Deposit token revenue 
		/*List<TokenRevenue> depositTokenRevenue = saleReport.tokenRevenues(calendar.getTime(), DEPOSIT_REVENUE);
		
		int depositTotalNumberOfTokens = depositTokenRevenue.size();
		
		for(TokenRevenue obj : depositTokenRevenue) {
			depositTotalAmount = depositTotalAmount + obj.getAmount();
		}*/
		
		//add to jasper report
		
		
		//Payments token revenue 
		/*float paymentTotalAmount = 0;
		
		List<TokenRevenue> paymentTokenRevenue = saleReport.tokenRevenues(calendar.getTime(), DEPOSIT_REVENUE);
		
		int paymentTotalNumberOfTokens = depositTokenRevenue.size();
		
		for(TokenRevenue obj : paymentTokenRevenue) {
			paymentTotalAmount = paymentTotalAmount + obj.getAmount();
		}*/
		
		//add to jasper report
		
		
		//sales finance metrics
		System.out.println(exportSaleFinanceMetricsReport("pdf", calendar.getTime()));
	}
	
	public String exportSaleFinanceMetricsReport(String reportFormat, Date date) throws FileNotFoundException, JRException {

		List<KeyValueModel> lp = saleReport.salesFinanceMetrics(date);
		String path = "/root/Desktop/reports";
		File file = ResourceUtils.getFile("classpath:salesfinancemetrics.jrxml");
		
		file.getAbsoluteFile();

		Map<String, Object> parameters = new HashMap<>();

		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lp);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

		if(reportFormat.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "/payments.html");
		}

		if(reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint, path + "/payments.pdf");
		}
		
		if(reportFormat.equalsIgnoreCase("xml")) {
			JasperExportManager.exportReportToXmlFile(jasperPrint, path + "/payments.xml", false);
		}

		return "";
	}

	
}
