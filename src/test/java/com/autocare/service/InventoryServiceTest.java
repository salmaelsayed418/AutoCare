
package com.autocare.service;

import com.autocare.exception.InsufficientStockException;
import com.autocare.repository.PartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import com.autocare.model.Part;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceTest {

    @TempDir
    Path tempDir;

    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        PartRepository repository =
                new PartRepository(tempDir.resolve("parts-test.csv"));

        inventoryService = new InventoryService(repository);
    }

    @Test
    void shouldAcceptValidStockQuantity() {
        assertDoesNotThrow(() ->
                inventoryService.validateStock("P003", 5)
        );
    }

    @Test
    void shouldThrowExceptionWhenStockIsInsufficient() {
        assertThrows(InsufficientStockException.class, () ->
                inventoryService.validateStock("P003", 100)
        );
    }

    @Test
    void shouldRejectZeroOrNegativeQuantity() {
        assertThrows(IllegalArgumentException.class, () ->
                inventoryService.validateStock("P003", 0)
        );

        assertThrows(IllegalArgumentException.class, () ->
                inventoryService.validateStock("P003", -2)
        );
    }

    @Test
    void shouldConsumePartAndUpdateStock() {

        inventoryService.consumePart("P003", 5);

        Part updatedPart = inventoryService.findPartById("P003");

        assertEquals(15, updatedPart.getStockQuantity());
    }
}
