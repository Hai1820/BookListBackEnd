package com.myclass.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @Email(message = "Email is mandatory")
    private String email;
    @Size(message = "Number phone must be a number and not longer than 9 numbers", max = 10)
    private String mobile;
    @Size(min = 6,message = "Password must have at least 6 characters ")
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
