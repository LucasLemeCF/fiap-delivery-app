package br.com.fiap.delivery.core.ports.outbound

import java.math.BigDecimal

interface CheckoutPort {

    fun checkout(
        customer: String,
        value: BigDecimal,
    ): Boolean

}