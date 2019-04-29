package com.fredastone.pandacore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.ApprovalReview;
;

@Repository
public interface ApprovalReviewRepository extends CrudRepository<ApprovalReview, String>{
	
	@Query(value="Select u from ApprovalReview u where u.approvalid = :approvalid order by u.createdon desc")
	List<ApprovalReview> findAllByapprovalid(@Param("approvalid")String approvalid);

}
