package br.com.fiap.delivery.infra.outbound

import br.com.fiap.delivery.core.domain.exceptions.InvalidException
import br.com.fiap.delivery.core.ports.outbound.CheckoutPort
import br.com.fiap.delivery.infra.outbound.clients.CheckoutClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class CheckoutAdapter(
    val checkoutClient: CheckoutClient
): CheckoutPort {

    override fun checkout(value: BigDecimal) {
        kotlin.runCatching {
            checkoutClient.checkout(value)
        }.fold (
            onSuccess = {
                logger.info("success to checkout with order value=$value")
            },
            onFailure = { throw InvalidException("checkout is impossible now for order value=$value") }
        )
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(CheckoutAdapter::class.java)
    }

}