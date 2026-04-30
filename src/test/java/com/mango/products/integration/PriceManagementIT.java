package com.mango.products.integration;

import com.mango.products.dto.CreatePriceRequest;
import com.mango.products.dto.CreateProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PriceManagementIT extends AbstractIntegrationTest {

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
    void shouldAddPriceToProduct() throws Exception {

        Long productId = createProduct();

        CreatePriceRequest request = new CreatePriceRequest(
                200.0,
                LocalDate.of(2024, 2, 1),
                productId
        );

        mockMvc.perform(post("/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetPriceByDate() throws Exception {

        Long productId = createProduct();

        mockMvc.perform(get("/prices/product/" + productId)
                        .param("date", "2024-02-01"))
                .andExpect(status().isOk());
    }
}
