package br.com.fiap.delivery.infra.inbound


import br.com.fiap.delivery.core.domain.OrderDomain
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
    fun confirmCheckout(@RequestParam paymentCode: String): ResponseEntity<OrderDomain> {
        return ResponseEntity.ok(
            checkoutPort.confirmCheckout(paymentCode)
        )
    }

}