package com.fredastone.pandacore.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.fredastone.pandacore.entity.Sale;

@Transactional
@Repository
public interface SaleRepository extends PagingAndSortingRepository<Sale, String>{
	
	@Query("Select s from Sale s where s.isreviewed = false and s.saletype = :saletype and salestatus = 1 and agentid = :agentid")
	Page<Sale> findAllUnverified(@Param("agentid") String agentid,@Param("saletype") String saletype, Pageable pageable);

	@Query("Select s from Sale s where s.isreviewed = true and s.saletype = :saletype and salestatus = 1 and agentid = :agentid")
	Page<Sale> findAllVerified(@Param("agentid") String agentid,@Param("saletype") String saletype, Pageable pageable);
	

	@Query("Select s from Sale s where agentid = :agentid")
	Page<Sale> findAllSaleByAgentId(@Param("agentid") String agentid, Pageable pageable);
	
	List<Sale> findAllByAgentid(String agentid);
	
	List<Sale> findAllByCustomerid(String customerid);

	Optional<Sale> findByScannedserial(String serial);
	
	@Query("Select s from Sale s where s.customerid = :customerid")
	List<Sale> findAllByCustomerid(@Param("customerid") String customerId, Pageable pageable);
	
	@Query("Select s from Sale s where s.customerid = :customerid and s.saletype = :saletype")
	List<Sale> findAllByCustomeridAndSaletype(@Param("customerid")String customerid, @Param("saletype") String saletype);
	
	@Query("Select s from Sale s where s.agentid = :agentid and s.saletype = :saletype")
	List<Sale> findAllByAgentidAndSaletype(@Param("agentid") String agentid, @Param("saletype")String saletype);
	
}

