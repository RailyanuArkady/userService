package org.user_service.services.integration.controller;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.user_service.dto.request.PassportRequestDTO;
import org.user_service.dto.request.UserRequestDTO;
import org.user_service.dto.response.CreateUserResponseDTO;
import org.user_service.dto.response.PassportResponseDTO;
import org.user_service.dto.response.UserResponseDTO;
import org.user_service.model.PersonSex;
import org.user_service.repository.UserRepository;
import org.user_service.services.util.AbstractIntegrationTests;
import org.user_service.services.util.TestUtils;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;


class UserControllerTest extends AbstractIntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @Test
    void TestCreateUser() throws Exception {
        UserRequestDTO userRequestDTO = TestUtils.buildUserRequestDTO();
        CreateUserResponseDTO createUserResponseDTO = new CreateUserResponseDTO(TestUtils.MOCKED_UUID);

        try (MockedStatic<UUID> utilities = Mockito.mockStatic(UUID.class)) {
            utilities.when(UUID::randomUUID).thenReturn(TestUtils.MOCKED_UUID);
            mockMvc.perform(MockMvcRequestBuilders.post("/public/api/v1/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtils.OBJECT_MAPPER.writeValueAsString(userRequestDTO)))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.content().json(TestUtils.OBJECT_MAPPER.writeValueAsString(createUserResponseDTO)));
        }
    }

    @Test
    void TestGetUserById() throws Exception {
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                "8 (921) 313-16-64",
                PersonSex.MALE,
                UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"),
                LocalDateTime.now().toLocalDate(),
                new PassportResponseDTO(
                "4444",
                "555555",
                "strong",
                "333333",
                LocalDateTime.now().toLocalDate()));
        String userResponseDTOJson = TestUtils.OBJECT_MAPPER.writeValueAsString(userResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/public/api/v1/users/3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(userResponseDTOJson));
        assertTrue(userRepository.existsById(3L));
    }

    @Test
    void TestUpdateUserByID() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO("+79213399964",
                PersonSex.MALE,
                UUID.randomUUID(),
                LocalDateTime.now().toLocalDate(),
                new PassportRequestDTO("2222",
                "999999",
                "Division name",
                "987655",
                LocalDateTime.now().toLocalDate()));
        String userRequestDTOJson = TestUtils.OBJECT_MAPPER.writeValueAsString(userRequestDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/public/api/v1/users/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRequestDTOJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(userRequestDTOJson));
    }

    @Test
    void TestDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/public/api/v1/users/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void TestUserNotFoundException() throws Exception{
        String nonExistUser = "/public/api/v1/users/999";

        mockMvc.perform(MockMvcRequestBuilders.get(nonExistUser))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Entity not found or marked as Deleted"));
    }
}