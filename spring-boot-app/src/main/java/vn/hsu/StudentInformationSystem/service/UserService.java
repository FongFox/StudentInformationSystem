package vn.hsu.StudentInformationSystem.service;

import vn.hsu.StudentInformationSystem.model.User;

import java.util.List;

public interface UserService {

    User handleCreateUser(User newUser);

    long handleCheckUserQuantity();

    void handleDeleteUserById(long id);

    User handleFetchUserById(long id);

    User handleFetchUserByUsername(String username);

    List<User> handleFetchUserList();

    void handleUpdateUser();

    User handleUpdateUserPassword(long id, String password);

    void initSampleData();
}
