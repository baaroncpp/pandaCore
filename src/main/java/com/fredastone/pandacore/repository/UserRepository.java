package com.fredastone.pandacore.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import com.fredastone.pandacore.entity.User;


/**
 * 
 */

@Repository
public interface UserRepository extends CrudRepository<User, String>,PagingAndSortingRepository<User, String> {
    
	Optional<User> findById(String id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    
    Page<User> findAllByisapproved(Pageable pageable,boolean approved);

    Page<User> findAllByusertype(Pageable pageable,String usertype);

    Page<User> findAllByisactive(Pageable pageable,boolean isactive);
    
    @Query("Select u from User u where u.usertype = 'customer' and u.id = :id")
    Optional<User> findCustomerById(@Param("id") String id);
    
    @Query("Select u from User u where u.username = :user or u.email = :user")
    Optional<User> findLoginUser(@Param("user") String user);
}
