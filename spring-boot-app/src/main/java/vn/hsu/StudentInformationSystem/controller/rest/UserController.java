package vn.hsu.StudentInformationSystem.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hsu.StudentInformationSystem.model.PasswordDto;
import vn.hsu.StudentInformationSystem.model.User;
import vn.hsu.StudentInformationSystem.service.UserService;
import vn.hsu.StudentInformationSystem.service.mapper.PasswordMapper;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final PasswordMapper passwordMapper;

    public UserController(UserService userService, PasswordMapper passwordMapper) {
        this.userService = userService;
        this.passwordMapper = passwordMapper;
    }

    @GetMapping()
    public ResponseEntity<List<User>> getUserList() {
        List<User> responseUserList = this.userService.handleFetchUserList();

        return ResponseEntity.status(HttpStatus.OK).body(responseUserList);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserDetail(@PathVariable long id) {
        User responseUser = this.userService.handleFetchUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        User responseUser = userService.handleCreateUser(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @PutMapping("{id}")
    public String updateUser(@PathVariable long id, @RequestBody User user) {
        return "Update user feature!";
    }

    @PatchMapping("{id}/pwd")
    public ResponseEntity<User> updateUserPassword(@PathVariable long id, @RequestBody PasswordDto passwordDto) {
        String password = this.passwordMapper.handleConvertPasswordDtoToPassword(passwordDto);
        User responseUser = this.userService.handleUpdateUserPassword(id, password);

        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        this.userService.handleDeleteUserById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
