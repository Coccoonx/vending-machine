package com.challenge.vendingmachine.service;

import com.challenge.vendingmachine.model.User;


public interface UserService extends CrudDefService<User, Long> {

   User findByUsername(String username);
}
