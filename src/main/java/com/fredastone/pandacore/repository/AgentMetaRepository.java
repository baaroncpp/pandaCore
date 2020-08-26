package com.fredastone.pandacore.repository;

import java.util.Date;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.fredastone.pandacore.entity.AgentMeta;
import com.fredastone.pandacore.entity.User;

public interface AgentMetaRepository extends CrudRepository<AgentMeta, String> ,PagingAndSortingRepository<AgentMeta, String> {

	public Page<AgentMeta> findByIsactiveTrue(Pageable pageable);
	
	public Page<AgentMeta> findByIsactiveFalse(Pageable pageable);
	
	Optional<AgentMeta> findByUserid(String id);
	
	Optional<User> findByUser(User user);
	
	long countByCreatedonBeforeAndCreatedonAfter(Date beforeDate, Date afterDate);
	
}
