package com.fredastone.pandacore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.Approver;

@Repository
public interface ApprovalRepository extends CrudRepository<Approver, String> {

}
