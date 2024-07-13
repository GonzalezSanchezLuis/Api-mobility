package com.transportapi.services;

import com.transportapi.model.entity.User;

public interface UserInterface {
   User saveUser(User user);
   User findUserById(Long id);
   User updateUser(Long id, User user);
   void userDelete(Long id);

}
