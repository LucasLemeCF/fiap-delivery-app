package br.com.fiap.delivery.core.usecases

import br.com.fiap.delivery.core.domain.enums.OrderStatus
import br.com.fiap.delivery.core.ports.inbound.CheckoutPort
import br.com.fiap.delivery.core.ports.inbound.OrderPort
import org.springframework.stereotype.Service

@Service
class CheckoutManager(
    val checkoutPort: br.com.fiap.delivery.core.ports.outbound.CheckoutPort,
    val orderPort: OrderPort,
): CheckoutPort {

    override fun checkout(orderId: Long) {
        val orderDomain = orderPort.searchOrder(orderId)

        checkoutPort.checkout(value = orderDomain.price)

        orderDomain.updateStatus(OrderStatus.IN_PREPARATION)
        orderPort.updateOrder(orderDomain)
    }

}