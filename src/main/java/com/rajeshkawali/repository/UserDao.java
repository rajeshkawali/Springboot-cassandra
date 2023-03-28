package com.rajeshkawali.repository;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.rajeshkawali.model.User;

/**
 * @author Rajesh_Kawali
 *
 */
@Repository
public interface UserDao extends CassandraRepository<User, UUID> {

}