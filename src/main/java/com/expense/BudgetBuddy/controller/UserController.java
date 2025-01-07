package com.expense.BudgetBuddy.controller;

import com.expense.BudgetBuddy.entity.User;
import com.expense.BudgetBuddy.entity.UserModel;
import com.expense.BudgetBuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> readUser(){
        return new ResponseEntity<User>(userService.readUser(),HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateUser(@RequestBody UserModel user){
        return new ResponseEntity<User>(userService.updateUser(user),HttpStatus.OK);
    }

    @DeleteMapping("/deactivate")
    public ResponseEntity<HttpStatus> deleteUser(){
        userService.deleteUser();
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

}
