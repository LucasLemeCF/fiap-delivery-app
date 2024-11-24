package br.com.fiap.delivery.core.ports.outbound

import br.com.fiap.delivery.core.domain.OrderDomain

interface OrderRepositoryPort {

    fun createOrder(order: OrderDomain): OrderDomain
    fun searchBy(id: Long): OrderDomain
    fun update(order: OrderDomain): OrderDomain

}