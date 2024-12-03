package br.com.fiap.delivery.infra.outbound

import br.com.fiap.delivery.core.domain.exceptions.InvalidException
import br.com.fiap.delivery.core.ports.outbound.CheckoutPort
import br.com.fiap.delivery.infra.outbound.clients.CheckoutClient
import br.com.fiap.delivery.infra.support.CheckoutRequest
import br.com.fiap.delivery.infra.support.ItemCheckoutRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class CheckoutAdapter(
    val checkoutClient: CheckoutClient
): CheckoutPort {

    override fun checkout(value: BigDecimal): String {
        return kotlin.runCatching {
            val checkoutRequest = CheckoutRequest(
                items = listOf(
                    ItemCheckoutRequest(
                        title = DEFAULT_TITLE,
                        unitPrice = value
                    )
                )
            )

            checkoutClient.checkout(checkoutRequest)
        }.fold (
            onSuccess = {
                logger.info("success to checkout with order value=$value, link=${it.paymentLink}")
                it.externalReference
            },
            onFailure = { throw InvalidException("checkout is impossible now for order value=$value, message=${it.message}") }
        )
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(CheckoutAdapter::class.java)
        private const val DEFAULT_TITLE = "Total value of order"
    }

}