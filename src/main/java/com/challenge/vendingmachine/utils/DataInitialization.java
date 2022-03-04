package com.challenge.vendingmachine.utils;

import com.challenge.vendingmachine.model.Role;
import com.challenge.vendingmachine.repository.RoleRepository;
import com.challenge.vendingmachine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataInitialization {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    private void initDataBase(){

        long count = roleRepository.count();

        if(count == 0){
            Role role = new Role("BUYER");
            roleRepository.save(role);

            role = new Role("SELLER");
            roleRepository.save(role);

            role = new Role("MODERATOR");
            roleRepository.save(role);
        }

    }
}
