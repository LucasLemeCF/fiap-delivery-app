package br.com.fiap.delivery.core.ports.outbound

import br.com.fiap.delivery.core.domain.OrderProductDomain

interface OrderProductRepositoryPort {

    fun createAll(orderProducts: List<OrderProductDomain>): List<OrderProductDomain>

}