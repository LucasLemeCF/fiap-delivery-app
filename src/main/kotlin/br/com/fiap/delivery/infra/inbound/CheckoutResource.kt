package br.com.fiap.delivery.infra.inbound


import br.com.fiap.delivery.core.ports.inbound.CheckoutPort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/checkout")
class CheckoutResource(
    val checkoutPort: CheckoutPort
) {

    @PostMapping
    fun checkout(
        @RequestParam orderId: Long,
    ): ResponseEntity<*> {
        return ResponseEntity.ok(
            checkoutPort.checkout(orderId)
        )
    }

}