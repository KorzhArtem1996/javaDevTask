package ua.korzh.testtask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.korzh.testtask.security.model.AppUser;
import ua.korzh.testtask.security.model.Role;
import ua.korzh.testtask.service.user.UserService;
import java.util.Set;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public AppUser getUserById(@PathVariable("id") Long id) {

        return userService.getUserById(id);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AppUser register(@RequestParam("username") String username, @RequestParam("password") String password) {

        return userService.createUser(username, password, Set.of(Role.USER));
    }

}
