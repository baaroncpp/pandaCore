package com.fredastone.pandacore.repository;

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

}

