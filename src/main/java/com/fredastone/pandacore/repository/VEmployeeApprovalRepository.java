package com.fredastone.pandacore.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fredastone.pandacore.entity.VEmployeeApprovals;

public interface VEmployeeApprovalRepository extends CrudRepository<VEmployeeApprovals, String> {

	List<VEmployeeApprovals> findByExternalReference(String externalReference);
}
