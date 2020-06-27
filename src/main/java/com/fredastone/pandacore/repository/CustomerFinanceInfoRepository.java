package com.fredastone.pandacore.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.fredastone.pandacore.entity.VCustomerFinanceInfo;

public interface CustomerFinanceInfoRepository extends CrudRepository<VCustomerFinanceInfo, String>{

	Optional<VCustomerFinanceInfo> findBydeviceserial(String scannedSerial);
}
