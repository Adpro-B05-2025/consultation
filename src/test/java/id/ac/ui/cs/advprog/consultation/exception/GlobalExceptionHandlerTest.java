package id.ac.ui.cs.advprog.consultation.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleNotFound() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Consultation", "id", 1L);
        ResponseEntity<String> response = handler.handleNotFound(ex);
        assertEquals(404, response.getStatusCode().value());
        assertTrue(response.getBody().contains("not found"));
    }
}
