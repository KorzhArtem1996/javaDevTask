package ua.korzh.testtask.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.korzh.testtask.exception.SuchEmailAlreadyExists;
import ua.korzh.testtask.security.model.AppUser;
import ua.korzh.testtask.security.model.Role;
import ua.korzh.testtask.repository.AppUserRepository;
import javax.transaction.Transactional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl implements UserService {
    
    private static final ConcurrentHashMap.KeySetView<String, Boolean> EMAILS = ConcurrentHashMap.newKeySet();

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

        if (!EMAILS.add(username)) {
            throw new SuchEmailAlreadyExists(String.format("User with email '%s' already exists", username));
        }

        var appUser = new AppUser(username, passwordEncoder.encode(password), roles);
        return appUserRepository.save(appUser);
    }

    @Override
    @Transactional
    public void deleteUser(String username) {

        if (EMAILS.remove(username)) {
            appUserRepository.getUserByUsername(username).ifPresent(user -> appUserRepository.delete(user));
        }
    }

    @Override
    @Transactional
    public void deleteAllUsers() {

        EMAILS.clear();
        appUserRepository.deleteAll();
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
