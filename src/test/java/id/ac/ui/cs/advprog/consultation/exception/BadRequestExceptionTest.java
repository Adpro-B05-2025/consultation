package id.ac.ui.cs.advprog.consultation.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BadRequestExceptionTest {

    @Test
    void testDefaultConstructor() {
        BadRequestException ex = new BadRequestException();
        assertNull(ex.getMessage());
    }

    @Test
    void testMessageConstructor() {
        BadRequestException ex = new BadRequestException("msg");
        assertEquals("msg", ex.getMessage());
    }

    @Test
    void testMessageCauseConstructor() {
        Throwable cause = new RuntimeException("cause");
        BadRequestException ex = new BadRequestException("msg", cause);
        assertEquals("msg", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testCauseConstructor() {
        Throwable cause = new RuntimeException("cause");
        BadRequestException ex = new BadRequestException(cause);
        assertEquals(cause, ex.getCause());
    }
}
