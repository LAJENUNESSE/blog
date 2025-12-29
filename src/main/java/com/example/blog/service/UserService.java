package com.example.blog.service;

import com.example.blog.dto.response.UserDTO;
import com.example.blog.entity.User;
import com.example.blog.exception.BusinessException;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDTO::fromEntity);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("User not found"));
        return UserDTO.fromEntity(user);
    }

    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> BusinessException.notFound("User not found"));
        return UserDTO.fromEntity(user);
    }

    @Transactional
    public UserDTO updateUser(Long id, String nickname, String avatar) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("User not found"));

        if (nickname != null) {
            user.setNickname(nickname);
        }
        if (avatar != null) {
            user.setAvatar(avatar);
        }

        userRepository.save(user);
        return UserDTO.fromEntity(user);
    }

    @Transactional
    public void changePassword(Long id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw BusinessException.badRequest("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Transactional
    public void toggleUserStatus(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("User not found"));
        user.setEnabled(!user.getEnabled());
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw BusinessException.notFound("User not found");
        }
        userRepository.deleteById(id);
    }
}
