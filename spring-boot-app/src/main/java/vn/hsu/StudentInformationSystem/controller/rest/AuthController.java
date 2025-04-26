package vn.hsu.StudentInformationSystem.controller.rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.hsu.StudentInformationSystem.model.dto.LoginDto;
import vn.hsu.StudentInformationSystem.model.dto.ResLoginDto;
import vn.hsu.StudentInformationSystem.util.SecurityUtils;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtils securityUtils;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtils securityUtils) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtils = securityUtils;
    }

    @PostMapping("login")
    public ResponseEntity<ResLoginDto> login(@Valid @RequestBody LoginDto loginDto) {
        //Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()
        );

        //xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication = authenticationManagerBuilder
                .getObject()
                .authenticate(authenticationToken);

        //create access token
        String accessToken = this.securityUtils.createToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        ResLoginDto resLoginDto = new ResLoginDto();
        resLoginDto.setAccessToken(accessToken);

        return ResponseEntity.status(HttpStatus.OK).body(resLoginDto);
    }
}
