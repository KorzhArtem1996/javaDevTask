package ua.korzh.testtask.security.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.korzh.testtask.security.model.Role;
import ua.korzh.testtask.service.user.UserService;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class RegistrationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var isAdmin = request.getHeader("admin");
        if (isAdmin != null) {
            var username = request.getParameter("username");
            var password = request.getParameter("password");
            userService.createUser(username, password, Set.of(Role.USER, Role.ADMIN));
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        return !(request.getServletPath().contains("/register") && request.getMethod().equals("POST"));
    }
}
