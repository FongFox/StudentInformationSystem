package vn.hsu.StudentInformationSystem.service;

import vn.hsu.StudentInformationSystem.model.PasswordDto;
import vn.hsu.StudentInformationSystem.model.User;

import java.util.List;

public interface UserService {
    public User handleCreateUser(User newUser);

    public long handleCheckUserQuantity();

    public void initSampleData();

    public void handleDeleteUserById(long id);

    public User handleFetchUserById(long id);

    public List<User> handleFetchUserList();

    public void handleUpdateUser();

    public User handleUpdateUserPassword(long id, String password);

    public String handleConvertPasswordDtoToPassword(PasswordDto passwordDto);
}
