package edu.icesi.pensamiento_computacional.services;

import java.util.List;

import edu.icesi.pensamiento_computacional.model.UserAccount;

public interface UserService {

    UserAccount createUser(UserAccount user);
    UserAccount updateUser(Integer id, UserAccount user);
    void deleteUser(Integer id);
    UserAccount getUserById(Integer id);
    List<UserAccount> getAllUsers();
}
