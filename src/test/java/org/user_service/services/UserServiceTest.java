package org.user_service.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.user_service.dto.request.PassportRequestDTO;
import org.user_service.dto.request.UserRequestDTO;
import org.user_service.dto.response.PassportResponseDTO;
import org.user_service.dto.response.UserResponseDTO;
import org.user_service.mapper.PassportMapper;
import org.user_service.mapper.UserMapper;
import org.user_service.model.Passport;
import org.user_service.model.PersonSex;
import org.user_service.model.User;
import org.user_service.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    private static User user;
    private static UserRequestDTO userRequestDTO;
    private static UserResponseDTO userResponseDTO;
    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private static final Long USER_ID = 1L;
    private static final LocalDate LOCAL_DATE = LocalDate.of(2024, 3, 1);

    @BeforeEach
    void setUp() {
        PassportMapper passportMapper = Mappers.getMapper(PassportMapper.class);
        ReflectionTestUtils.setField(userMapper, "passportMapper", passportMapper);

        userService = new UserService(userRepository, userMapper);
        PassportRequestDTO passportRequestDTO = new PassportRequestDTO("1234", "123456", "string", "123456", LOCAL_DATE);
        userRequestDTO = new UserRequestDTO("8 (921) 331-11-64", PersonSex.MALE, UUID.randomUUID(), LOCAL_DATE, passportRequestDTO);

        Passport passport = new Passport();
        passport.setId(USER_ID);
        passport.setPassportSeries("1234");
        passport.setPassportNumber("567890");
        passport.setPassportDivisionName("Division");
        passport.setPassportDivisionCode("123456");
        passport.setPassportDateOfIssue(LOCAL_DATE);
        passport.setUser(user);

        user = new User();
        user.setId(USER_ID);
        user.setExternalId(UUID.randomUUID());
        user.setPhone("+79213311164");
        user.setSex(PersonSex.FEMALE);
        user.setPhotoId(UUID.randomUUID());
        user.setDeleted(false);
        user.setBirthdate(LOCAL_DATE);
        user.setCreatedAt(LocalDateTime.now());
        user.setModifiedAt(LocalDateTime.now());
        user.setPassport(passport);

        PassportResponseDTO passportResponseDTO = new PassportResponseDTO(passport.getPassportSeries(), passport.getPassportNumber(), passport.getPassportDivisionName(), passport.getPassportDivisionCode(), passport.getPassportDateOfIssue());
        userResponseDTO = new UserResponseDTO(user.getPhone(), user.getSex(), user.getPhotoId(), user.getBirthdate(), passportResponseDTO);
    }

    @Test
    void TestSaveUser() {
        when(userMapper.dtoToUser(userRequestDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        UUID externalId = userService.saveUser(userRequestDTO);

        verify(userRepository, times(1)).save(user);
        assertEquals(user.getExternalId(), externalId);
    }

    @Test
    void TestFindUserById() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.ofNullable(user));
        when(userMapper.userToResponseDTO(user)).thenReturn(userResponseDTO);

        UserResponseDTO userResponseDTOTest = userService.findUserById(USER_ID);

        verify(userRepository, times(1)).findById(USER_ID);
        assertEquals(userResponseDTO, userResponseDTOTest);
    }

    @Test
    void TestUpdateUser() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.ofNullable(user));

        userResponseDTO = userService.updateUser(USER_ID, userRequestDTO);

        verify(userMapper, times(1)).updateUserFromDTO(userRequestDTO, user);
        assertEquals(PersonSex.MALE, userResponseDTO.sex());
    }

    @Test
    void TestDeleteUser() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.ofNullable(user));
        userService.deleteUser(USER_ID);
        assertAll(
                () -> assertTrue(user.isDeleted()),
                () -> assertTrue(Optional.of(user.getPassport()).isPresent())
        );

    }
}