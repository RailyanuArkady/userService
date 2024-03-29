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

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UUID saveUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.dtoToUser(userRequestDTO);
        return userRepository.save(user).getExternalId();
    }

    public UserResponseDTO findUserById(Long id) {
        return userMapper.userToResponseDTO(userRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userMapper.updateUserFromDTO(userRequestDTO, user);
        return userMapper.userToResponseDTO(user);
    }

    @Transactional
    public void deleteUser (Long id){
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        user.setDeleted(true);
    }
}
