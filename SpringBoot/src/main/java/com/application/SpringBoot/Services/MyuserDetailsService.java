package com.application.SpringBoot.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.application.SpringBoot.Config.UserPrinciple;
import com.application.SpringBoot.Model.User;
import com.application.SpringBoot.Repository.UserRepo;

@Service
public class MyuserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepo userrepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userrepo.findByUsername(username);
    
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
    
        return new UserPrinciple(user);
    }

}
