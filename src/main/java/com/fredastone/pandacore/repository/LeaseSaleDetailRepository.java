package com.fredastone.pandacore.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fredastone.pandacore.entity.VLeaseSaleDetails;

@Repository
public interface LeaseSaleDetailRepository extends PagingAndSortingRepository<VLeaseSaleDetails, String>{

	@Query("Select u from VLeaseSaleDetails u where u.isreviewed = :isreviewed")
	Page<VLeaseSaleDetails> findByisreviewed(@Param("isreviewed") boolean isreviewed,Pageable pageable);
	
}
