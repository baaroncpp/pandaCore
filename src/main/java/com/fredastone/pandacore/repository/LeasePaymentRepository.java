package com.fredastone.pandacore.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fredastone.pandacore.entity.LeasePayment;

@Repository
public interface LeasePaymentRepository extends CrudRepository<LeasePayment, String>, PagingAndSortingRepository<LeasePayment, String> {
	
	@Query("Select l from LeasePayment l where leaseid = :leaseid")
	List<LeasePayment> findAllByleaseid(@Param("leaseid") String leaseid);
	
	@Query("Select l from LeasePayment l where createdon = :createdon")
	List<LeasePayment> findAllBycreatedon(@Param("createdon")Date createdon);
	
	Page<LeasePayment> findAll(Pageable pageable);

}
