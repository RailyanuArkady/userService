package org.user_service.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.user_service.dto.request.UserRequestDTO;
import org.user_service.dto.response.UserResponseDTO;
import org.user_service.mapper.UserMapper;
import org.user_service.model.User;
import org.user_service.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    //на будущее
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public UUID saveUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.dtoToUser(userRequestDTO);
        return userRepository.save(user).getExternalId();
    }

    public UserResponseDTO findUserById(Long id) {
        return userMapper.userToResponseDTO(userRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }
}
