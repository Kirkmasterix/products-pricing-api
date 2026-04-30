package com.mango.products.integration;

import com.mango.products.dto.CreateProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductQueryIT extends AbstractIntegrationTest {

    private Long createProduct() throws Exception {

        CreateProductRequest request = new CreateProductRequest(
                "Zapatillas",
                "modelo test",
                100.0,
                LocalDate.of(2024, 1, 1)
        );

        String response = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(response).get("id").asLong();
    }

    @Test
    void shouldGetProductWithPrices() throws Exception {

        Long productId = createProduct();

        mockMvc.perform(get("/products/" + productId + "/prices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prices").exists());
    }
}