package com.challenge.vendingmachine.controller;

import com.challenge.vendingmachine.model.User;
import com.challenge.vendingmachine.service.UserService;
import com.challenge.vendingmachine.model.dto.UserDTO;
import com.challenge.vendingmachine.model.dto.UserRegistration;
import com.challenge.vendingmachine.utils.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserRegistration userRegistration) {

        User user = new User();
        user.setUsername(userRegistration.getUsername());
        user.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
        if(userRegistration.getRoles() != null && !userRegistration.getRoles().isEmpty()){
            user.setRoles(userMapper.rolesFromStrings(userRegistration.getRoles()));
        }
        User created = userService.create(user);
        return ResponseEntity.ok(userMapper.toDTO(created));
    }

    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<UserDTO> update(@PathVariable("id") Long id, @Valid @RequestBody UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User updated = userService.update(id, user);
        return ResponseEntity.ok(userMapper.toDTO(userService.update(id, updated)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll().stream()
                .filter(Objects::nonNull)
                .map(userMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<UserDTO>> findAllPaged(Pageable pageable) {
        return ResponseEntity.ok(userService.findAllPaged(pageable).map(userMapper::toDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userMapper.toDTO(userService.findById(id)));
    }

    @GetMapping("/username/{name}")
    public ResponseEntity<UserDTO> findByUsername(@PathVariable("name") String name) {
        return ResponseEntity.ok(userMapper.toDTO(userService.findByUsername(name)));
    }
}
