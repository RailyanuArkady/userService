package org.user_service.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.user_service.dto.request.UserRequestDTO;
import org.user_service.dto.response.GetUserResponseDTO;
import org.user_service.mapper.UserMapper;
import org.user_service.model.User;
import org.user_service.repository.UserRepository;

import java.util.List;
import java.util.Optional;
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

    public GetUserResponseDTO findUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        boolean deleteStatus = optionalUser.filter(user -> !user.isDeleted()).isPresent();
        if (!deleteStatus) {
            throw new EntityNotFoundException("Entity not found or marked as Deleted");
        }
        return userMapper.userToResponseDTO(optionalUser.get());
    }
}
