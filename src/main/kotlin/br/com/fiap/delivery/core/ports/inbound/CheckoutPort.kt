package br.com.fiap.delivery.core.ports.inbound

import br.com.fiap.delivery.core.domain.OrderDomain

interface CheckoutPort {

    fun confirmCheckout(paymentCode: String): OrderDomain

}