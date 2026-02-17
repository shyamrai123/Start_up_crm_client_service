package com.example.Start_up_crm_client_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class SignupRequest {
    @NotNull(message = "FirstName is required")
    @NotEmpty(message = "FirstName cannot be empty")
    private String firstName;
    @NotNull(message = "LastName is required")
    @NotEmpty(message = "LastName cannot be empty")
    private String lastName;
    @NotNull(message = "Username is required")
    @NotEmpty(message = "Username cannot be empty")
    private String username;
    @NotNull(message = "Email is required")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password cannot be empty")
    private String password;
    @NotNull(message = "Mobile number is required")
    @NotEmpty(message = "Mobile number cannot be empty")
    private String mobile_no;


    private String bio;

    private String location;


    private Set<String> role;

    public Set<String> getRole() {
        return (role != null) ? role : new HashSet<>();
    }
}