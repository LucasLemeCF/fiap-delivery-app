package br.com.fiap.delivery.adapters.outbound

import br.com.fiap.delivery.core.ports.outbound.CheckoutPort
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class CheckoutAdapter: CheckoutPort {

    override fun checkout(customer: String, value: BigDecimal): Boolean {
        TODO("Not yet implemented")
    }

}