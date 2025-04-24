package vn.hsu.StudentInformationSystem.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.hsu.StudentInformationSystem.service.impl.UserServiceImpl;

@Configuration
public class DataInitializer {

    private final UserServiceImpl userService;

    public DataInitializer(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            // Đoạn code sẽ được chạy khi ứng dụng khởi động
            System.out.println("Application has started!");

            // Ví dụ: Thực hiện khởi tạo dữ liệu
            initialDefaultUsers();

            System.out.println("Application Still running at: ");
            System.out.println("localhost:8080/api");
        };
    }

    private void initialDefaultUsers() {
        long numberDBUser = userService.handleCheckUserQuantity();
        if (numberDBUser == 0) {
            this.userService.initSampleData();
            System.out.println("Add default users complete!");
        } else {
            System.out.println("Already have default users!");
        }
    }
}
