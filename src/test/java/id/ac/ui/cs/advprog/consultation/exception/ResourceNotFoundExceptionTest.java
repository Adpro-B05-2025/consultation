package id.ac.ui.cs.advprog.consultation.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {

    @Test
    void testMessageFormat() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Consultation", "id", 1L);
        assertEquals("Consultation not found with id = 1", ex.getMessage());
    }
}
