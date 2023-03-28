package com.rajeshkawali.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rajeshkawali.model.User;

/**
 * @author Rajesh_Kawali
 *
 */
@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

	@AllowFiltering
	@Query("SELECT * FROM User WHERE firstName = ?0 ORDER BY lastName ASC")
	List<User> findByUserName(String name);
}