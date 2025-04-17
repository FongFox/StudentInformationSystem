package vn.hsu.StudentInformationSystem.service.impl;

import org.springframework.stereotype.Service;
import vn.hsu.StudentInformationSystem.model.PasswordDto;
import vn.hsu.StudentInformationSystem.model.Student;
import vn.hsu.StudentInformationSystem.repository.StudentRepository;
import vn.hsu.StudentInformationSystem.service.StudentService;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student handleCreateStudent(Student newStudent) {
        // Step 1: Save the student temporarily to get an auto-generated ID
        Student savedStudent = this.studentRepository.save(newStudent);

        // Step 2: Generate the username using naming format
        String baseUsername = generateBaseUsername(savedStudent);
        String finalUsername = baseUsername + savedStudent.getId();
        savedStudent.setUsername(finalUsername);

        // Step 3: Save again with final username
        return this.studentRepository.save(savedStudent);
    }

    private String generateBaseUsername(Student student) {
        String first = removeDiacritics(student.getFirstName().toLowerCase());
        String lastInitial = removeDiacritics(student.getLastName().substring(0, 1).toLowerCase());

        StringBuilder middleInitials = new StringBuilder();
        for (String word : student.getMiddleName().split(" ")) {
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

    public long handleCheckStudentQuantity() {
        return studentRepository.count();
    }

    public void initSampleData() {
        handleCreateStudent(new Student("Phong", "Gia Nguyên", "Trần", "123456"));
        handleCreateStudent(new Student("An", "Văn", "Nguyễn", "123456"));
        handleCreateStudent(new Student("Tú", "", "Lê", "123456"));
    }

    public void handleDeleteStudentById(long id) {
        this.studentRepository.deleteById(id);
    }

    public Student handleFetchStudentById(long id) {
        Optional<Student> studentOptional = this.studentRepository.findById(id);
        return studentOptional.isPresent() ? studentOptional.get() : null;
    }

    public List<Student> handleFetchStudentList() {
        return this.studentRepository.findAll();
    }

    public Student handleUpdateStudent(long id, Student student) {
        Student studentDb = handleFetchStudentById(id);
        if (studentDb == null) {
            return null;
        } else {
            studentDb.setPassword(student.getPassword());
        }

        return this.studentRepository.save(studentDb);
    }

    public Student handleUpdateStudentPassword(long id, String password) {
        Student studentDb = handleFetchStudentById(id);
        if (studentDb == null) {
            return null;
        } else {
            studentDb.setPassword(password);
        }

        return this.studentRepository.save(studentDb);
    }

    public String handleConvertPasswordDtoToPassword(PasswordDto passwordDto) {
        return passwordDto.getPassword();
    }
}
