package vn.hsu.StudentInformationSystem.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class LoadingDelayFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String delayHeader = request.getHeader("delay");
        if (delayHeader != null) {
            try {
                long delay = Long.parseLong(delayHeader);
                Thread.sleep(delay);
            } catch (NumberFormatException | InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        filterChain.doFilter(request, response);
    }
}
