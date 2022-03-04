package com.challenge.vendingmachine.controller;

import com.challenge.vendingmachine.model.User;
import com.challenge.vendingmachine.service.UserService;
import com.challenge.vendingmachine.service.dto.UserDTO;
import com.challenge.vendingmachine.service.dto.UserRegistration;
import com.challenge.vendingmachine.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
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

    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserRegistration userRegistration) {

        User user = new User();
        user.setUsername(userRegistration.getUsername());
        user.setPassword(userRegistration.getPassword());
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