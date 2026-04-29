package com.mango.products.unit;

import com.mango.products.domain.Price;
import com.mango.products.domain.Product;
import com.mango.products.dto.CreatePriceRequest;
import com.mango.products.exception.InvalidPriceDateException;
import com.mango.products.exception.PriceNotFoundException;
import com.mango.products.repository.PriceRepository;
import com.mango.products.repository.ProductRepository;
import com.mango.products.service.impl.PriceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private PriceService priceService;

    // 🟢 1. Crear precio sin existente
    @Test
    void shouldCreateNewPrice_whenNoCurrentPriceExists() {
        Product product = new Product();
        product.setId(1L);

        CreatePriceRequest request = new CreatePriceRequest(
                100.0,
                LocalDate.of(2024, 1, 1),
                1L
        );

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(priceRepository.findFirstByProductIdAndEndDateIsNull(1L)).thenReturn(Optional.empty());
        when(priceRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Price result = priceService.addPrice(request);

        assertNotNull(result);
        assertNull(result.getEndDate());

        verify(priceRepository).save(any());
    }

    // 🟡 2. Crear precio cerrando el anterior
    @Test
    void shouldCloseCurrentPrice_andCreateNewOne() {
        Product product = new Product();
        product.setId(1L);

        Price current = new Price();
        current.setInitDate(LocalDate.of(2024, 1, 1));

        CreatePriceRequest request = new CreatePriceRequest(
                200.0,
                LocalDate.of(2024, 2, 1),
                1L
        );

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(priceRepository.findFirstByProductIdAndEndDateIsNull(1L))
                .thenReturn(Optional.of(current));
        when(priceRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Price result = priceService.addPrice(request);

        assertEquals(LocalDate.of(2024, 1, 31), current.getEndDate());
        assertNull(result.getEndDate());

        verify(priceRepository, times(2)).save(any());
    }

    //Validación: fecha anterior inválida
    @Test
    void shouldThrowException_whenNewDateBeforeCurrent() {
        Product product = new Product();
        product.setId(1L);

        Price current = new Price();
        current.setInitDate(LocalDate.of(2024, 2, 1));

        CreatePriceRequest request = new CreatePriceRequest(
                200.0,
                LocalDate.of(2024, 1, 1),
                1L
        );

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(priceRepository.findFirstByProductIdAndEndDateIsNull(1L))
                .thenReturn(Optional.of(current));

        RuntimeException ex = assertThrows(InvalidPriceDateException.class,
                () -> priceService.addPrice(request));

        assertTrue(ex.getMessage().contains("cannot be before"));
    }

    // Obtener precio por fecha OK
    @Test
    void shouldReturnPrice_whenExistsForDate() {
        Price price = new Price();

        when(priceRepository.findActivePrice(1L, LocalDate.now()))
                .thenReturn(Optional.of(price));

        Price result = priceService.getPriceForDate(1L, LocalDate.now());

        assertNotNull(result);
    }

    //Obtener precio por fecha NO existe
    @Test
    void shouldThrowException_whenNoPriceForDate() {
        when(priceRepository.findActivePrice(1L, LocalDate.now()))
                .thenReturn(Optional.empty());

        assertThrows(PriceNotFoundException.class,
                () -> priceService.getPriceForDate(1L, LocalDate.now()));
    }
}
