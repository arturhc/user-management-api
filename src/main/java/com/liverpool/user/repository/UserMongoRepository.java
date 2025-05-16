package com.liverpool.user.repository;

import com.liverpool.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<User, String> {

  boolean existsByEmail(String email);

}
