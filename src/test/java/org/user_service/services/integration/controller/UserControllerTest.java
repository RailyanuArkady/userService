package org.user_service.services.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.user_service.dto.request.PassportRequestDTO;
import org.user_service.dto.request.UserRequestDTO;
import org.user_service.model.PersonSex;
import org.user_service.repository.UserRepository;
import org.user_service.services.util.TestUtils;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setUp(){
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
    @Test
    void TestCreateUser() throws Exception {
        UserRequestDTO userRequestDTO = TestUtils.buildUserRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8081/public/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        assertTrue(userRepository.existsById(TestUtils.MOCKED_ID));
    }

    @Test
    void TestGetUserById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8081/public/api/v1/users/3"))
                .andExpect(MockMvcResultMatchers.status().isOk());
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

        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8081/public/api/v1/users/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(userRequestDTO)));
    }

    @Test
    void TestDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8081/public/api/v1/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void TestUserNotFoundException() throws Exception{
        String nonExistUser = "http://localhost:8081/public/api/v1/users/999";

        mockMvc.perform(MockMvcRequestBuilders.get(nonExistUser))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Entity not found or marked as Deleted"));
    }
}