package com.expense.BudgetBuddy.service;

import com.expense.BudgetBuddy.entity.User;
import com.expense.BudgetBuddy.entity.UserModel;
import com.expense.BudgetBuddy.exceptions.ItemAlreadyExistsException;
import com.expense.BudgetBuddy.exceptions.ResourceNotFoundException;
import com.expense.BudgetBuddy.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public User createUser(UserModel user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new ItemAlreadyExistsException("User is already register with email"+user.getEmail());
        }
        User newUser = new User();
        BeanUtils.copyProperties(user,newUser);
        newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public User readUser() {
       Long userId = getLoggedInUser().getId();
        return userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found for the id"+ userId));

    }

    @Override
    public User updateUser(UserModel user) {
       User existingUser  = readUser();
       existingUser.setName(user.getName() != null ? user.getName() : existingUser.getName());
       existingUser.setEmail(user.getEmail() != null? user.getEmail() : existingUser.getEmail());
       existingUser.setPassword(user.getPassword() != null? bcryptEncoder.encode(user.getPassword()) : existingUser.getEmail());
       existingUser.setAge(user.getAge() != null? user.getAge() : existingUser.getAge());
       return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser() {
        User existingUser = readUser();
        userRepository.delete(existingUser);
    }

    @Override
    public User getLoggedInUser() {
       Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

      String email = authentication.getName();
      return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found for the email"+ email));

    }
}
