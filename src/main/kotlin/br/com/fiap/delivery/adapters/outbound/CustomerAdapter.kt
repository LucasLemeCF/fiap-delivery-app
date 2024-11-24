package br.com.fiap.delivery.adapters.outbound

import br.com.fiap.delivery.core.ports.outbound.CustomerPort
import org.springframework.stereotype.Component

@Component
class CustomerAdapter: CustomerPort {

    override fun check(customer: String) {
        TODO("Not yet implemented")
    }

}