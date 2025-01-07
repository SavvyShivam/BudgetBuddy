package com.expense.BudgetBuddy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserModel {

    @NotBlank(message = "name should not be empty")
    private String name;

    @NotNull(message = "Email should not be empty")
    private String email;

    @NotNull(message = "password should not be empty")
    @Size(min=5,message = "password should be at least 5 characters")
    private String password;

    private Long age=0L;


}
