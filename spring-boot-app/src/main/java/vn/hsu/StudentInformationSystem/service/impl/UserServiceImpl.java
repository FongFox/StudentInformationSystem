package vn.hsu.StudentInformationSystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.hsu.StudentInformationSystem.model.User;
import vn.hsu.StudentInformationSystem.repository.UserRepository;
import vn.hsu.StudentInformationSystem.service.UserService;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User handleCreateUser(User newUser) {
        //Hash password for new user
        String hashPassword = this.passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(hashPassword);

        //Save the User temporarily to get an auto-generated ID
        User savedUser = this.userRepository.save(newUser);

        //Generate the username using naming format
        String baseUsername = generateBaseUsername(savedUser);
        String finalUsername = baseUsername + savedUser.getId();
        savedUser.setUsername(finalUsername);

        //Save again with final username
        return this.userRepository.save(savedUser);
    }

    private String generateBaseUsername(User user) {
        String first = removeDiacritics(user.getFirstName().toLowerCase());
        String lastInitial = removeDiacritics(user.getLastName().substring(0, 1).toLowerCase());

        StringBuilder middleInitials = new StringBuilder();
        for (String word : user.getMiddleName().split(" ")) {
            if (!word.isBlank()) {
                middleInitials.append(word.substring(0, 1).toLowerCase());
            }
        }

        return first + "." + lastInitial + middleInitials;
    }

    private String removeDiacritics(String input) {
        if (input == null) {
            return "";
        }
        // Step 1: Convert to decomposed Unicode (e.g. ú → u + ́ )
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        // Step 2: Remove diacritic marks (accents)
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }

    @Override
    public long handleCheckUserQuantity() {
        return userRepository.count();
    }

    @Override
    public void handleDeleteUserById(long id) {
//        this.userRepository.deleteById(id);
        User userDb = handleFetchUserById(id);
        this.userRepository.delete(userDb);
    }

    @Override
    public User handleFetchUserById(long id) {
        Optional<User> UserOptional = this.userRepository.findById(id);

        return UserOptional.orElseThrow(
                () -> new EntityNotFoundException("User with ID " + id + " not found")
        );
    }

    @Override
    public User handleFetchUserByUsername(String username) {
        // TODO Auto-generated method stub
        Optional<User> UserOptional = this.userRepository.findByUsername(username);

        return UserOptional.orElseThrow(
                () -> new EntityNotFoundException("User with username or password not found")
        );
    }

    @Override
    public List<User> handleFetchUserList() {
        return this.userRepository.findAll();
    }

    @Override
    public void handleUpdateUser() {
    }

    @Override
    public User handleUpdateUserPassword(long id, String password) {
        User userDb = handleFetchUserById(id);
        String hashPassword = this.passwordEncoder.encode(password);
        userDb.setPassword(hashPassword);

        return this.userRepository.save(userDb);
    }

    @Override
    public void initSampleData() {
        handleCreateUser(new User("Phong", "Trần", "Gia Nguyên", "123456"));
        handleCreateUser(new User("An", "Văn", "Nguyễn", "123456"));
        handleCreateUser(new User("Tú", "Lê", "", "123456"));
        handleCreateUser(new User("A", "Nguyễn", "Văn", "123456"));
        handleCreateUser(new User("Sơn", "Nguyễn", "Hồng", "123456"));
    }
}
