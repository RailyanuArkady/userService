package org.user_service.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.user_service.dto.PassportDTO;
import org.user_service.mapper.UserAndPassportMapper;
import org.user_service.mapper.UserAndPassportMapperImpl;
import org.user_service.model.Passport;
import org.user_service.repository.PassportRepository;

@Service
@Transactional(readOnly = true)
public class PassportServiceClass {
    private final PassportRepository passportRepository;

    UserAndPassportMapper userAndPassportMapper = new UserAndPassportMapperImpl();
    public PassportServiceClass(PassportRepository passportRepository) {
        this.passportRepository = passportRepository;
    }

    @Transactional
    public void save (PassportDTO passportDTO){
        Passport passport = userAndPassportMapper.passportDTOToPassport(passportDTO);
        passportRepository.save(passport);
    }
}
