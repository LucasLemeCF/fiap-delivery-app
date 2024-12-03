package br.com.fiap.delivery.infra.outbound

import br.com.fiap.delivery.core.domain.OrderDomain
import br.com.fiap.delivery.core.domain.exceptions.NotFoundException
import br.com.fiap.delivery.core.ports.outbound.OrderRepositoryPort
import br.com.fiap.delivery.infra.mappers.OrderMapper
import br.com.fiap.delivery.infra.outbound.repositories.OrderRepository
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

    override fun searchBy(id: Long): OrderDomain {
        return orderRepository.findById(id).map {
            OrderMapper.toDomain(it)
        }.orElseThrow { throw NotFoundException("not found order id=$id in system!") }
    }

    override fun update(order: OrderDomain): OrderDomain {
        return OrderMapper.toDomain(
            orderRepository.save(
                OrderMapper.toEntity(order)
            )
        )
    }

}