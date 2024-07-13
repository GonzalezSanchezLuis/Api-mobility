package com.transportapi.model.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.transportapi.model.entity.User;

@Repository
public interface UserDao extends CrudRepository<User,Long>{
   public User findUserByEmail(String email); 
}
