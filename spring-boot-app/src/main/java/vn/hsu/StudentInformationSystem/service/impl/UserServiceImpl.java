package vn.hsu.StudentInformationSystem.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import vn.hsu.StudentInformationSystem.model.PasswordDto;
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

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User newUser) {
        // Step 1: Save the User temporarily to get an auto-generated ID
        User savedUser = this.userRepository.save(newUser);

        // Step 2: Generate the username using naming format
        String baseUsername = generateBaseUsername(savedUser);
        String finalUsername = baseUsername + savedUser.getId();
        savedUser.setUsername(finalUsername);

        // Step 3: Save again with final username
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

    public long handleCheckUserQuantity() {
        return userRepository.count();
    }

    public void initSampleData() {
        handleCreateUser(new User("Phong", "Gia Nguyên", "Trần", "123456"));
        handleCreateUser(new User("An", "Văn", "Nguyễn", "123456"));
        handleCreateUser(new User("Tú", "", "Lê", "123456"));
    }

    public void handleDeleteUserById(long id) {
//        this.userRepository.deleteById(id);
        User userDb = handleFetchUserById(id);
        this.userRepository.delete(userDb);
    }

    public User handleFetchUserById(long id) {
        Optional<User> UserOptional = this.userRepository.findById(id);

        return UserOptional.orElseThrow(
                () -> new EntityNotFoundException("User with ID " + id + " not found")
        );
    }

    public List<User> handleFetchUserList() {
        return this.userRepository.findAll();
    }

    public void handleUpdateUser() {}

    public User handleUpdateUserPassword(long id, String password) {
        User userDb = handleFetchUserById(id);
        userDb.setPassword(password);

        return this.userRepository.save(userDb);
    }

    public String handleConvertPasswordDtoToPassword(PasswordDto passwordDto) {
        return passwordDto.getPassword();
    }
}
