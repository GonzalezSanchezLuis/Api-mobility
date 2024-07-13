package com.transportapi.services.impl;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.transportapi.exceptions.InvalidUserException;
import com.transportapi.model.dao.UserDao;
import com.transportapi.model.entity.User;
import com.transportapi.services.UserInterface;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserImpl implements UserInterface{
    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public User saveUser(User user){
        User existingUser = userDao.findUserByEmail(user.getEmail());
        if (existingUser != null) {
            throw new InvalidUserException("Ya existe un usuario registrado con ese email.");
        }else{
            String encodePasword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePasword);
            return userDao.save(user);
        }
    }

    @Override
    public User findUserById(Long id) {
        Optional<User> optionalUser = userDao.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new EntityNotFoundException("No se encontró ningún usuario con este ID");
        }
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userDao.findById(id).orElseThrow(() -> new EntityNotFoundException("No hay un usuario con este ID"));

        if(user.getName() != null || user.getEmail() != null || user.getPassword() != null){
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            String passEncode = passwordEncoder.encode(user.getPassword());
            existingUser.setPassword(passEncode);
        }
        return userDao.save(existingUser);
    }

    @Override
    public void userDelete(Long id) {
      User userToDelete = userDao.findById(id).orElseThrow(()-> new EntityNotFoundException("No hay un usuario con este ID"));
      userDao.delete(userToDelete);
    }   
}
