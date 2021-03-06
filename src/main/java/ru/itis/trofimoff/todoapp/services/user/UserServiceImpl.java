package ru.itis.trofimoff.todoapp.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.trofimoff.todoapp.dto.SignUpFormDto;
import ru.itis.trofimoff.todoapp.dto.UserDto;
import ru.itis.trofimoff.todoapp.dto.UserStatisticsDto;
import ru.itis.trofimoff.todoapp.models.User;
import ru.itis.trofimoff.todoapp.repositories.jpa.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(SignUpFormDto signUpFormDto) {
        User user = new User(signUpFormDto);
        user.setConfirmCode(UUID.randomUUID().toString());
        String hashPassword = passwordEncoder.encode(signUpFormDto.getPassword());
        user.setPassword(hashPassword);
        this.userRepository.save(user);
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);
        return user.map(UserDto::new);
    }

    @Override
    public boolean equalsRowPasswordWithHashPassword(String rowPassword, String hashPassword) {
        return passwordEncoder.matches(rowPassword, hashPassword);
    }

    public UserStatisticsDto getUserStatistic(int userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(value -> new UserStatisticsDto(value.getAllTodos(), value.getDoneTodos())).orElse(null);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> {
            userDtos.add(new UserDto(user));
        });
        return userDtos;
    }

    @Override
    public List<UserDto> findAllDefaultUsers() {
        List<User> users = userRepository.findAllDefaultUsers();
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> {
            userDtos.add(new UserDto(user));
        });
        return userDtos;
    }

    @Override
    public void confirmUser(String code) {
        userRepository.confirmUser(code);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User saveForOauth(User user) {
        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
            user.setConfirmCode(UUID.randomUUID().toString());
            user.setType(User.Type.VK);
            return userRepository.save(user);
        } else {
            return userRepository.findByEmail(user.getEmail()).get();
        }
    }
}
