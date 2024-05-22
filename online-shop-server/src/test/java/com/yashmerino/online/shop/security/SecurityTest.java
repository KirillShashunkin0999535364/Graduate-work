package com.yashmerino.online.shop.security;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Tests for security part of the application.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityTest {

    /**
     * MVC Mock.
     */
    @Autowired
    private MockMvc mvc;

    /**
     * Test that any endpoint and request that is not specified in {@link SecurityConfig}
     * is secured and throws unauthorized it requested without JWT Token.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    void anyRequestIsSecuredTest() throws Exception {
        mvc.perform(delete("/api/auth/register")).andExpect(status().isUnauthorized());
    }
}
