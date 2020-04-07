package com.fredastone.pandacore.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.fredastone.pandacore.entity.CustomerMeta;

public interface CustomerMetaRepository extends PagingAndSortingRepository<CustomerMeta, String>{

	Page<CustomerMeta> findAll(Pageable pageable);
	
	Optional<CustomerMeta> findByidnumber(String idNumber);
	
}
