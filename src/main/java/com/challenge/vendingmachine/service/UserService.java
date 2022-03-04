package com.challenge.vendingmachine.service;

import com.challenge.vendingmachine.model.User;


public interface UserService extends ICrudDefServiceInterface<User, Long>{

   User findByUsername(String username);
}
