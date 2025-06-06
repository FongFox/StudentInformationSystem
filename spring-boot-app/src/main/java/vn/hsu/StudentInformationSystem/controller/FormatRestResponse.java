package vn.hsu.StudentInformationSystem.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import vn.hsu.StudentInformationSystem.service.dto.RestResponse;

@RestControllerAdvice
public class FormatRestResponse implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int status = servletResponse.getStatus();
        RestResponse<Object> restResponse = new RestResponse<Object>();

        if (body instanceof String) {
            return body;
        }

        if (status < 400) {
            // case success
            restResponse.setStatus(status);
            restResponse.setMessage("Call API Success!");
            restResponse.setData(body);
        } else {
            //case error
            return body;
        }

        return restResponse;
    }
}
