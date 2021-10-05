package ua.korzh.testtask.service.user;

import ua.korzh.testtask.security.model.AppUser;
import ua.korzh.testtask.security.model.Role;

import java.util.Set;

public interface UserService {

    AppUser getUserById(Long userId);

    AppUser createUser(String username, String password, Set<Role> roles);

    void deleteUser(String username);

    AppUser updateUser(AppUser appUser);

    boolean userExists(String username);

    AppUser getUserByUsername(String username);

    void changePassword(String username, String newPassword);

}
