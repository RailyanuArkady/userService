package org.user_service.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.user_service.dto.PassportDTO;
import org.user_service.dto.UserDTO;
import org.user_service.mapper.UserAndPassportMapper;
import org.user_service.mapper.UserAndPassportMapperImpl;
import org.user_service.model.Passport;
import org.user_service.model.User;
import org.user_service.repository.PassportRepository;
import org.user_service.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserServiceClass {

    private final UserRepository userRepository;
    private final PassportRepository passportRepository;
    UserAndPassportMapper userAndPassportMapper = new UserAndPassportMapperImpl();

    public UserServiceClass(UserRepository userRepository, PassportRepository passportRepository) {
        this.userRepository = userRepository;
        this.passportRepository = passportRepository;
    }

    //на будущее
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional
    public UUID save(UserDTO userDTO){
            User user = userAndPassportMapper.dtoToUser(userDTO);
            PassportDTO passportDTO = userDTO.getPassport();
            Passport passport = userAndPassportMapper.passportDTOToPassport(passportDTO);
            userRepository.save(user);
            passport.setUser(userRepository.findById(user.getId()).orElseThrow(EntityNotFoundException::new));
            passportRepository.save(passport);
            return user.getExternalId();
    }

    //на будущее
    public User findOne (long id){
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }
}
