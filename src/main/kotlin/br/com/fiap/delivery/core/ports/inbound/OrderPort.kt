package br.com.fiap.delivery.core.ports.inbound

import br.com.fiap.delivery.core.domain.CompleteOrderDomain
import br.com.fiap.delivery.core.domain.OrderDomain
import br.com.fiap.delivery.core.domain.OrderFlatDomain

interface OrderPort {

    fun createOrder(orderFlatDomain: OrderFlatDomain): OrderDomain
    fun getOrderBy(id: Long): CompleteOrderDomain
    fun searchOrder(id: Long): OrderDomain
    fun updateOrder(orderDomain: OrderDomain): OrderDomain

}