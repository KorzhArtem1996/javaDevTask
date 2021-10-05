package ua.korzh.testtask.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.korzh.testtask.security.model.AppUser;
import ua.korzh.testtask.security.model.Role;
import ua.korzh.testtask.repository.AppUserRepository;
import javax.transaction.Transactional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AppUser getUserById(Long userId) {

        return appUserRepository.findById(userId).get();
    }

    @Override
    @Transactional
    public AppUser getUserByUsername(String username) {

        return appUserRepository.getUserByUsername(username).get();
    }

    @Override
    @Transactional
    public AppUser createUser(String username, String password, Set<Role> roles) {

        var appUser = new AppUser(username, passwordEncoder.encode(password), roles);
        return appUserRepository.save(appUser);
    }

    @Override
    @Transactional
    public void deleteUser(String username) {

        appUserRepository.getUserByUsername(username).ifPresent(user -> appUserRepository.delete(user));
    }

    @Override
    @Transactional
    public AppUser updateUser(AppUser appUser) {

        return appUserRepository.saveAndFlush(appUser);
    }

    @Override
    @Transactional
    public boolean userExists(String username) {

        return appUserRepository.getUserByUsername(username).isPresent();
    }

    @Override
    @Transactional
    public void changePassword(String username, String newPassword) {

        var appUser = getUserByUsername(username);
        appUser.setPassword(newPassword);
        updateUser(appUser);
    }
}
