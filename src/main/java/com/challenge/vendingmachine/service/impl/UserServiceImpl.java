package com.challenge.vendingmachine.service.impl;

import com.challenge.vendingmachine.exception.EntityAlreadyExistException;
import com.challenge.vendingmachine.exception.EntityNotExistException;
import com.challenge.vendingmachine.model.Role;
import com.challenge.vendingmachine.model.User;
import com.challenge.vendingmachine.repository.RoleRepository;
import com.challenge.vendingmachine.repository.UserRepository;
import com.challenge.vendingmachine.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User create(User user) throws EntityAlreadyExistException {
        log.info("Creating user {}", user);
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new EntityAlreadyExistException("Username " + user.getUsername() + " already exist");
        }
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            Set<Role> role = new HashSet<>();
            for (Role roleString : user.getRoles()) {
                role.add(roleRepository.getById(roleString.getName()));
            }
            user.setRoles(role);
        }

        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) throws EntityNotExistException {
        if (id == null || !userRepository.findById(id).isPresent())
            throw new EntityNotExistException("Invalid User id : " + id);

        log.info("Updating user {}", user);
        log.info("Updating user ignoring username {}", user.getUsername());

        User exist = userRepository.findById(id).get();
        user.setId(id);
        user.setUsername(exist.getUsername());
        user.setPassword(exist.getPassword());

        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            Set<Role> role = new HashSet<>();
            for (Role roleString : user.getRoles()) {
                role.add(roleRepository.getById(roleString.getName()));
            }
            user.setRoles(role);
        } else
            user.setRoles(new HashSet<>());

        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) throws EntityNotExistException {
        User p = findById(id);
        if (p != null) {
            log.info("Deleting user with id {}", id);
            userRepository.delete(p);
        } else
            throw new EntityNotExistException("Invalid User id: " + id);

    }

    @Override
    public List<User> findAll() {
        log.info("find All User");
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAllPaged(Pageable pageable) {
        log.info("find All User paged");
        return userRepository.findAll(pageable);
    }

    @Override
    public User findById(Long id) {
        log.info("find user by id {}", id);
        if (userRepository.findById(id).isPresent())
            return userRepository.findById(id).get();
        else
            throw new EntityNotExistException("Invalid User id: " + id);
    }

    @Override
    public User findByUsername(String userName) {
        log.info("find user by name {}", userName);
        return userRepository.findByUsername(userName);
    }
}
