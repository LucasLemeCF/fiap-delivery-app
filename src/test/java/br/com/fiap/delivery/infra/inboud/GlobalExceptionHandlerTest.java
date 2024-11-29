package br.com.fiap.delivery.infra.inboud;

import br.com.fiap.delivery.core.domain.exceptions.NotFoundException;
import br.com.fiap.delivery.infra.GlobalException;
import br.com.fiap.delivery.infra.inbound.GlobalExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private WebRequest webRequest; // More generic Mock for ExceptionHandler methods

    @InjectMocks
    private GlobalExceptionHandler handler;

    @Test
    void testNotFoundExceptionHandler_NotFoundException() {
        Exception exception = new NotFoundException("Resource not found");

        ResponseEntity<GlobalException> response = handler.defaultNotFoundExceptionHandler(request, exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(new GlobalException(
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND.name()
        ), response.getBody());
    }

    @Test
    void testNotFoundExceptionHandler_OtherException() {
        Exception exception = new RuntimeException("Unexpected error");

        ResponseEntity<GlobalException> response = handler.defaultNotFoundExceptionHandler(request, exception);

        verifyNoInteractions(request);
    }
}
