package com.fredastone.pandacore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.Approver;


@Repository
public interface ApproverRepository extends CrudRepository<Approver, String>{

	@Query("select u from Approver u where u.isenabled = true and u.approvalservice = :approvalService order by u.approvalorder desc")
	List<Approver> findByApprovalservice(@Param("approvalService")String approvalService);
}
