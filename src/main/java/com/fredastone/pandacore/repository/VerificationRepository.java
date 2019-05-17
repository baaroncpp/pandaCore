package com.fredastone.pandacore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.VerificationInfo;


@Repository
public interface VerificationRepository extends PagingAndSortingRepository<VerificationInfo, String> {
	
	@Query("Select u from VerificationInfo u where u.isreviewed = true and u.agentid = :agentid")
	Page<VerificationInfo> findAllVerified(@Param("agentid") String agentid,Pageable pageable);
	

	@Query("Select u from VerificationInfo u where u.isreviewed = false and u.agentid = :agentid")
	Page<VerificationInfo> findAllUnverified(@Param("agentid") String agentid,Pageable pageable);
	

}
