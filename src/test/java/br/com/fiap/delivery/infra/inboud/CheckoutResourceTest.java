package br.com.fiap.delivery.infra.inboud;

import br.com.fiap.delivery.core.ports.inbound.CheckoutPort;
import br.com.fiap.delivery.infra.inbound.CheckoutResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CheckoutResourceTest {

    @Mock
    private CheckoutPort checkoutPort;

    @InjectMocks
    private CheckoutResource checkoutResource;

    @Test
    void testCheckoutSuccess() {
        String orderId = "12345";

        ResponseEntity<?> response = checkoutResource.confirmCheckout(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(checkoutPort).confirmCheckout(orderId);
    }

}
