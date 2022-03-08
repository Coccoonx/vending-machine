package com.challenge.vendingmachine.utils.mapper;

import com.challenge.vendingmachine.model.Role;
import com.challenge.vendingmachine.model.User;
import com.challenge.vendingmachine.model.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(user.getUsername());
            userDTO.setId(user.getId());
            userDTO.setDeposit(user.getDeposit());
            userDTO.setRoles(user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet()));
            return userDTO;
        }
        return null;
    }

    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            User user = new User();
            user.setId(userDTO.getId());
            user.setUsername(userDTO.getUsername());
            user.setDeposit(userDTO.getDeposit());
            Set<Role> authorities = this.rolesFromStrings(userDTO.getRoles());
            user.setRoles(authorities);
            return user;
        }
    }

    public Set<Role> rolesFromStrings(Set<String> rolesAsString) {
        Set<Role> roles = new HashSet<>();

        if (rolesAsString != null) {
            roles = rolesAsString.stream().map(string -> {
                Role role = new Role();
                role.setName(string);
                return role;
            }).collect(Collectors.toSet());
        }

        return roles;
    }

    public List<User> userDTOsToUsers(List<UserDTO> userDTOs) {
        return userDTOs.stream()
                .filter(Objects::nonNull)
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public List<UserDTO> usersToUserDTOs(List<User> users) {
        return users.stream()
                .filter(Objects::nonNull)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
