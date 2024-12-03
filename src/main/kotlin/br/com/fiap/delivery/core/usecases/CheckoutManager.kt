package br.com.fiap.delivery.core.usecases

import br.com.fiap.delivery.core.domain.OrderDomain
import br.com.fiap.delivery.core.domain.enums.OrderStatus
import br.com.fiap.delivery.core.ports.inbound.CheckoutPort
import br.com.fiap.delivery.core.ports.inbound.OrderPort
import org.springframework.stereotype.Service

@Service
class CheckoutManager(
    val orderPort: OrderPort,
): CheckoutPort {

    override fun confirmCheckout(paymentCode: String): OrderDomain {
        val orderDomain = orderPort.searchOrderBy(paymentCode)
        orderDomain.updateStatus(OrderStatus.IN_PREPARATION)

        return orderPort.updateOrder(orderDomain)
    }

}