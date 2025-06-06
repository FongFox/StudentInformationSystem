package vn.hsu.StudentInformationSystem.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import vn.hsu.StudentInformationSystem.service.dto.RestResponse;

import java.io.IOException;


@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final AuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();

    private final ObjectMapper mapper;

    public CustomAuthenticationEntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // Let the BearerTokenAuthenticationEntryPoint write WWW-Authenticate header, status, etc.
        this.delegate.commence(request, response, authException);

        response.setContentType("application/json;charset=UTF-8");

        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.setMessage("Call API Failed!");

        // Safe‐null check for authException.getCause()
        String causeMsg = authException.getCause() != null
                ? authException.getCause().getMessage()
                : authException.getMessage();

//        res.setError("(Invalid token) " + authException.getCause().getMessage());
        res.setError("(Invalid token) " + causeMsg);

        mapper.writeValue(response.getWriter(), res);
    }
}
