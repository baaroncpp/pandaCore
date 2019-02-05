package com.fredastone.security.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fredastone.pandacore.entity.User;

/**
 * Created by stephan on 20.03.16.
 */

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    com.fredastone.pandacore.entity.User findById(String id);
}
