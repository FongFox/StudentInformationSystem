package vn.hsu.StudentInformationSystem.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.hsu.StudentInformationSystem.model.Student;
import vn.hsu.StudentInformationSystem.service.StudentService;

@Configuration
public class DataInitializer {
    private final StudentService studentService;

    public DataInitializer(StudentService studentService) {
        this.studentService = studentService;
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            // Đoạn code sẽ được chạy khi ứng dụng khởi động
            System.out.println("Application has started!");

            // Ví dụ: Thực hiện khởi tạo dữ liệu
            initialDefaultStudents();

            System.out.println("Application Still running at: ");
            System.out.println("localhost:8080/api/v1");
        };
    }

    private void initialDefaultStudents() {
        long numberDBStudent = studentService.handleCheckStudentQuantity();
        if(numberDBStudent == 0) {
            Student newStudent = new Student("An", "Văn", "Nguyễn", "an.nv1", "123456");
            this.studentService.handleCreateStudent(newStudent);
            System.out.println("Add default student complete!");
        } else {
            System.out.println("Already have default student!");
        }
    }
}
