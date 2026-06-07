package Springinterview.interviews.service;

import Springinterview.interviews.dto.UserDto;
import Springinterview.interviews.repository.UserRepository;
import Springinterview.interviews.entity.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User createUser(UserDto userDto) {

        if (userDto.getEmail() == null) {
            throw new IllegalArgumentException("Email is required");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}