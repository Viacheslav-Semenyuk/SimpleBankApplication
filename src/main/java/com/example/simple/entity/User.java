package com.example.simple.entity;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "users")
@ToString(exclude = "cards")
public class User extends BaseEntity {


    @NotBlank(message = "Please provide a username")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Please provide a password")
    @Column
    private String password;

    @NotBlank(message = "Please provide a email")
    @Column
    private String email;

    @NotBlank(message = "Please provide a last name")
    @Column
    private String lastName;

    @NotBlank(message = "Please provide a first name")
    @Column
    private String firstName;

    @NotBlank(message = "Please provide a birthday")
    @Column
    private String birthday;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Card> cards;


    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
