package vn.hsu.StudentInformationSystem.service.mapper;

import org.springframework.stereotype.Service;
import vn.hsu.StudentInformationSystem.model.dto.PasswordDto;

@Service
public class PasswordMapper {
    public String handleConvertPasswordDtoToPassword(PasswordDto passwordDto) {
        return passwordDto.getPassword();
    }
}
