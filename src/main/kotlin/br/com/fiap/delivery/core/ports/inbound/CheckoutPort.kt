package br.com.fiap.delivery.core.ports.inbound

interface CheckoutPort {

    fun checkout(orderId: Long)

}