package com.challenge.vendingmachine.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Role {

    public static final String BUYER = "BUYER";
    public static final String SELLER = "SELLER";

    @Id
    @Column(name = "role_id", length = 30)
    @NotNull
    @Size(max = 50)
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                '}';
    }
}
