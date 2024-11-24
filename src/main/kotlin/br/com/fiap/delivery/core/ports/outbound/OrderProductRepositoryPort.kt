package br.com.fiap.delivery.core.ports.outbound

import br.com.fiap.delivery.core.domain.OrderDomain
import br.com.fiap.delivery.core.domain.OrderProductDomain

interface OrderProductRepositoryPort {

    fun createAll(orderProducts: List<OrderProductDomain>): List<OrderProductDomain>
    fun findAllBy(order: OrderDomain): List<OrderProductDomain>

}