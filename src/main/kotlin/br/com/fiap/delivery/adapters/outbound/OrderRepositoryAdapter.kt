package br.com.fiap.delivery.adapters.outbound

import br.com.fiap.delivery.core.domain.OrderDomain
import br.com.fiap.delivery.core.ports.outbound.OrderRepositoryPort
import br.com.fiap.delivery.infra.mappers.OrderMapper
import br.com.fiap.delivery.infra.repositories.OrderRepository
import org.springframework.stereotype.Component

@Component
class OrderRepositoryAdapter(
    val orderRepository: OrderRepository,
): OrderRepositoryPort {

    override fun createOrder(order: OrderDomain): OrderDomain {
        return OrderMapper.toDomain(
            orderRepository.save(
                OrderMapper.toEntity(order)
            )
        )
    }

}