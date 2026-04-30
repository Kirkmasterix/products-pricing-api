package com.mango.products.integration;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductErrorIT extends AbstractIntegrationTest {

    @Test
    void shouldReturn404_whenProductDoesNotExist() throws Exception {

        mockMvc.perform(get("/products/999999/prices"))
                .andExpect(status().isNotFound());
    }
}