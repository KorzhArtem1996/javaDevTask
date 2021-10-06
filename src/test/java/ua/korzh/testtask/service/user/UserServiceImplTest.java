package ua.korzh.testtask.service.user;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.korzh.testtask.exception.SuchEmailAlreadyExists;
import ua.korzh.testtask.repository.AppUserRepository;
import ua.korzh.testtask.security.model.Role;
import java.util.Set;

@SpringBootTest
public class UserServiceImplTest {

    private static final String USERNAME = "artem";
    private static final String PASSWORD = "password";
    private static final Set<Role> ROLES = Set.of(Role.USER);

    @Autowired
    private UserService userService;
    @Autowired
    private AppUserRepository userRepository;

    @BeforeEach
    public void clearDb() {

        userService.deleteAllUsers();
    }

    @Test
    public void createUserTest() {

        var user = userService.createUser(USERNAME, PASSWORD, ROLES);

        assertEquals(USERNAME, user.getUsername());
        assertEquals(user.getPassword().length(), 60);
        assertEquals(ROLES, user.getRoles());
    }

    @Test
    public void createOneMoreUserWithTheSameUsername() {

        var user1 = userService.createUser(USERNAME, PASSWORD, ROLES);

        assertThrows(SuchEmailAlreadyExists.class, () -> {
            var user2 = userService.createUser(USERNAME, PASSWORD, ROLES);
        });
    }

    @Test
    public void getUserByIdTest() {

        var user = userService.createUser(USERNAME, PASSWORD, ROLES);

        var loadedUser = userService.getUserById(user.getId());

        assertEquals(user.getId(), loadedUser.getId());
    }

    @Test
    public void changePasswordTest() {

        var user = userService.createUser(USERNAME, PASSWORD, ROLES);
        var newPassword = "newPassword";

        userService.changePassword(user.getUsername(), newPassword);
        var loadedUser = userService.getUserByUsername(user.getUsername());

        assertEquals(newPassword, loadedUser.getPassword());
    }
}