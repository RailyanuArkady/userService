package org.user_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.user_service.dto.request.UserRequestDTO;
import org.user_service.mapper.PassportMapper;
import org.user_service.mapper.UserMapper;
import org.user_service.model.Passport;
import org.user_service.model.User;
import org.user_service.repository.PassportRepository;
import org.user_service.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceClass {

    private final UserRepository userRepository;
    private final PassportRepository passportRepository;
    private final UserMapper userMapper;
    private final PassportMapper passportMapper;

    //на будущее
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public UUID saveUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.dtoToUser(userRequestDTO);
        Passport passport = passportMapper.passportRequestDTOToPassport(userRequestDTO.passport());
        passport.setUser(user);
        return userRepository.save(user).getExternalId();
    }

    //на будущее
    public User findOne(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }
}
