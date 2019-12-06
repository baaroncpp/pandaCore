package com.fredastone.pandacore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.fredastone.pandacore.entity.Lease;

@Repository
public interface LeaseRepository extends CrudRepository<Lease, String>{

}
