package com.transportapi.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.transportapi.model.dao.UserDao;
import com.transportapi.model.entity.User;

@Service
public class UserDetailsImpl implements UserDetailsService{
   @Autowired
    private UserDao userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findUserByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return (UserDetails) user;
    }  
}
