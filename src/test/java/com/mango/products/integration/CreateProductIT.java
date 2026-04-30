package com.mango.products.integration;

import com.mango.products.dto.CreateProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CreateProductIT extends AbstractIntegrationTest {

    @Test
    void shouldCreateProduct() throws Exception {

        CreateProductRequest request = new CreateProductRequest(
                "Zapatillas",
                "modelo test",
                100.0,
                LocalDate.of(2024, 1, 1)
        );

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Zapatillas"));
    }
}
