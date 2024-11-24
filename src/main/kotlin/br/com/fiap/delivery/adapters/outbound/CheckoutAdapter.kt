package br.com.fiap.delivery.adapters.outbound

import br.com.fiap.delivery.core.ports.outbound.CheckoutPort
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class CheckoutAdapter: CheckoutPort {

    override fun checkout(customer: String, value: BigDecimal): Boolean {
        logger.info("checkout")
        return true
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(CheckoutAdapter::class.java)
    }

}