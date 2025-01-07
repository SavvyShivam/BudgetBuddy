package com.expense.BudgetBuddy.service;

import com.expense.BudgetBuddy.entity.User;
import com.expense.BudgetBuddy.entity.UserModel;

public interface UserService {

    User createUser(UserModel user);

    User readUser();

    User updateUser(UserModel user);

    void deleteUser();

    User getLoggedInUser();
}
