package com.fredastone.pandacore.reportMgt.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
import com.fredastone.pandacore.models.PaymentModel;
import com.fredastone.pandacore.repository.AgentMetaRepository;
import com.fredastone.pandacore.repository.CustomerMetaRepository;
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

	@Autowired
	private LeasePaymentRepository leasePaymentRepository;

	@Autowired
	private CustomerMetaRepository customerRepository;

	@Autowired
	private AgentMetaRepository agentRepository;
	
	@Autowired
	private SaleRepository saleRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Scheduled(cron = "0 0 12 * * ?")
	public void importReportEvent() {

	}

	public String exportReport(String reportFormat) throws FileNotFoundException, JRException {

		List<PaymentModel> lp = mapLeasePaymentsToPaymentModel();
		String path = "/root/Desktop/reports";
		File file = ResourceUtils.getFile("classpath:payments.html");
		
		file.getAbsoluteFile();
		

		Map<String, Object> parameters = new HashMap<>();

		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lp);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

		if(reportFormat.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "/payments.html");
		}

		if(reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "/payments.pdf");
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

	
	/*
	 * @Scheduled(fixedRate = 5000) public void reportCurrentTime() throws
	 * FileNotFoundException, JRException { exportReport("html");
	 * System.out.println("Scheduler test "+new Date()); }
	 */
	 
	
}
