package org.user_service.services.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;
<<<<<<< HEAD
import org.user_service.dto.request.PassportRequestDTO;
import org.user_service.dto.request.UserRequestDTO;
=======
>>>>>>> e3754af (task 12 findUserById unit test)
import org.user_service.dto.response.PassportResponseDTO;
import org.user_service.dto.response.UserResponseDTO;
import org.user_service.model.Passport;
import org.user_service.model.PersonSex;
import org.user_service.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

@UtilityClass
public class TestUtils {

    public static final UUID MOCKED_UUID = UUID.fromString("79d7176e-5714-442f-87c1-1de48a78ffee");
    public static final Long MOCKED_ID = 1L;
    public static final LocalDateTime MOCKED_DATE_TIME = LocalDateTime.now();
    public static final String PHONE = "+79217459553";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public User buildUser() {
        User user = User.builder()
                .id(MOCKED_ID)
                .externalId(MOCKED_UUID)
                .sex(PersonSex.MALE)
                .isDeleted(false)
                .phone(PHONE)
                .photoId(MOCKED_UUID)
                .birthdate(MOCKED_DATE_TIME.toLocalDate())
                .createdAt(MOCKED_DATE_TIME)
                .modifiedAt(MOCKED_DATE_TIME)
                .build();
        user.setPassport(buildPassport());
        return user;
    }

    public Passport buildPassport() {
        return Passport.builder()
                .id(MOCKED_ID)
                .externalId(MOCKED_UUID)
                .passportNumber("123456")
                .passportSeries("1234")
                .passportDivisionCode("123456")
                .passportDivisionName("Division name")
                .passportDateOfIssue(MOCKED_DATE_TIME.toLocalDate())
                .createdAt(MOCKED_DATE_TIME)
                .modifiedAt(MOCKED_DATE_TIME)
                .build();
    }

    public UserResponseDTO buildUserResponseDTO() {
        return new UserResponseDTO(PHONE,
                PersonSex.MALE,
                MOCKED_UUID,
                MOCKED_DATE_TIME.toLocalDate(),
                buildPassportResponseDTO());
    }

    public PassportResponseDTO buildPassportResponseDTO() {
        return new PassportResponseDTO("1234",
                "123456",
                "Division name",
                "123456",
                MOCKED_DATE_TIME.toLocalDate());
    }

    public PassportRequestDTO buildPassportRequestDTO(){
        return new PassportRequestDTO("1234",
                "123456",
                "Division name",
                "123456",
                MOCKED_DATE_TIME.toLocalDate());
    }

    public UserRequestDTO buildUserRequestDTO() {
        return new UserRequestDTO(PHONE,
                PersonSex.MALE,
                MOCKED_UUID,
                MOCKED_DATE_TIME.toLocalDate(),
                buildPassportRequestDTO());
    }

    public ObjectMapper objectMapperConfig(){
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return OBJECT_MAPPER;
    }
}
