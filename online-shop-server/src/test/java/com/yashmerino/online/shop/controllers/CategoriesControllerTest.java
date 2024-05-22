package com.yashmerino.online.shop.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link CategoryController}
 */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class CategoriesControllerTest {

    /**
     * Mock mvc to perform requests.
     */
    @Autowired
    private MockMvc mvc;

    /**
     * Test get categories.
     *
     * @throws Exception if something goes wrong.
     */
    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    void getCategoriesTest() throws Exception {
        MvcResult result = mvc.perform(get("/api/categories")).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("[{\"id\":1,\"name\":\"Digital Services\"},{\"id\":2,\"name\":\"Cosmetics and Body Care\"},{\"id\":3,\"name\":\"Food and Beverage\"},{\"id\":4,\"name\":\"Furniture and Decor\"},{\"id\":5,\"name\":\"Health and Wellness\"},{\"id\":6,\"name\":\"Household Items\"},{\"id\":7,\"name\":\"Media\"},{\"id\":8,\"name\":\"Pet Care\"},{\"id\":9,\"name\":\"Office Equipment\"}]"));
    }
}
