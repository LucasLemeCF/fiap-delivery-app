package br.com.fiap.delivery.core.ports.outbound

import java.math.BigDecimal

interface CheckoutPort {

    fun checkout(
        value: BigDecimal,
    )

}