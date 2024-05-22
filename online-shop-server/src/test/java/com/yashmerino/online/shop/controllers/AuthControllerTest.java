package com.yashmerino.online.shop.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yashmerino.online.shop.model.dto.auth.LoginDTO;
import com.yashmerino.online.shop.model.dto.auth.RegisterDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.yashmerino.online.shop.utils.Role.USER;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests {@link AuthController} endpoints.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@Transactional
@ActiveProfiles("test")
class AuthControllerTest {

    /**
     * MVC Mock.
     */
    @Autowired
    private MockMvc mvc;

    /**
     * Object mapper.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Register DTO.
     */
    private RegisterDTO registerDTO;

    /**
     * Login DTO.
     */
    private LoginDTO loginDTO;

    @BeforeEach
    void setup() {
        registerDTO = new RegisterDTO();
        registerDTO.setRole(USER);
        registerDTO.setUsername("test");
        registerDTO.setPassword("test");
        registerDTO.setEmail("test@test.test");

        loginDTO = new LoginDTO();
        loginDTO.setUsername("test");
        loginDTO.setPassword("test");
    }

    /**
     * Tests that /register endpoint works as expected.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    void registerSuccessfulTest() throws Exception {
        MvcResult result = mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("{\"status\":200,\"message\":\"user_registered_successfully\"}"));
    }

    /**
     * Tests /register with existing user.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    void registerExistingUserTest() throws Exception {
        mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isOk());

        MvcResult result = mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isConflict()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(",\"status\":409,\"error\":\"username_taken\"}"));
    }

    /**
     * Test if an email has no sign, is invalid and is not provided.
     */
    @ParameterizedTest
    @ValueSource(strings = {"nosign_email", "@invalid.mail", ""})
    void registerNoSignEmailTest() throws Exception {
        registerDTO.setEmail("nosign_email");

        MvcResult result = mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"fieldErrors\":[{\"field\":\"email\",\"message\":\"email_is_invalid\"}]}"));
    }

    /**
     * Tests /register without username.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    void registerNoUsername() throws Exception {
        registerDTO.setUsername("");

        MvcResult result = mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"field\":\"username\",\"message\":\"username_is_required\"}"));
        assertTrue(result.getResponse().getContentAsString().contains("{\"field\":\"username\",\"message\":\"username_too_short\"}"));
    }

    /**
     * Tests /register without body.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    void registerNoRequestBodyTest() throws Exception {
        mvc.perform(post("/api/auth/register")).andExpect(status().isBadRequest()).andReturn();
    }

    /**
     * Tests /register without password.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    void registerNoPasswordTest() throws Exception {
        registerDTO.setPassword("");

        MvcResult result = mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"field\":\"password\",\"message\":\"password_too_short\"}"));
        assertTrue(result.getResponse().getContentAsString().contains("{\"field\":\"password\",\"message\":\"password_is_required\"}"));
    }

    /**
     * Tests that /login endpoint works as expected.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    void loginSuccessfulTest() throws Exception {
        mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isOk());

        MvcResult result = mvc.perform(post("/api/auth/login").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(loginDTO))).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("Bearer "));
    }

    /**
     * Tests /login with a non-existing username.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    void usernameDoesntExistTest() throws Exception {
        MvcResult result = mvc.perform(post("/api/auth/login").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(loginDTO))).andExpect(status().isNotFound()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(",\"status\":404,\"error\":\"username_not_found\"}"));
    }

    /**
     * Tests /login without username.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    void loginNoUsernameTest() throws Exception {
        mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isOk());

        loginDTO.setUsername("");

        MvcResult result = mvc.perform(post("/api/auth/login").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(loginDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"fieldErrors\":[{\"field\":\"username\",\"message\":\"username_is_required\"}]}"));
    }

    /**
     * Tests /login without password.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    void loginNoPasswordTest() throws Exception {
        mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isOk());

        loginDTO.setPassword("");

        MvcResult result = mvc.perform(post("/api/auth/login").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(loginDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"fieldErrors\":[{\"field\":\"password\",\"message\":\"password_is_required\"}]}"));
    }

    /**
     * Tests /register with a username that is too long
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    void registerUsernameTooLongTest() throws Exception {
        registerDTO.setUsername("gaerghetrhatetaewrvtuaewtgarhgyjareghtyjargeytjgaeRJGYAERKGFHAGERKUGHERUKGHARUKGhgukraehgkuerahkughkurgaehukg");

        MvcResult result = mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"fieldErrors\":[{\"field\":\"username\",\"message\":\"username_too_long\"}]}"));
    }

    /**
     * Tests /register with a username that is too short
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    void registerUsernameTooShortTest() throws Exception {
        registerDTO.setUsername("baa");

        MvcResult result = mvc.perform(post("/api/auth/register").contentType(
                APPLICATION_JSON).content(objectMapper.writeValueAsString(registerDTO))).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("{\"fieldErrors\":[{\"field\":\"username\",\"message\":\"username_too_short\"}]}"));
    }
}
