package com.challenge.vendingmachine.service;

import com.challenge.vendingmachine.model.User;
import com.challenge.vendingmachine.repository.UserRepository;
import com.challenge.vendingmachine.security.VMUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VMUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null)
            return new VMUserDetails(user);
        else
            throw new UsernameNotFoundException("User " + username + " not found");
    }
}
