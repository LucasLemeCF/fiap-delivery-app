package br.com.fiap.delivery.infra.outbound

import br.com.fiap.delivery.core.domain.OrderDomain
import br.com.fiap.delivery.core.domain.OrderProductDomain
import br.com.fiap.delivery.core.ports.outbound.OrderProductRepositoryPort
import br.com.fiap.delivery.infra.mappers.OrderMapper
import br.com.fiap.delivery.infra.mappers.OrderProductMapper
import br.com.fiap.delivery.infra.outbound.repositories.OrderProductRepository
import org.springframework.stereotype.Component

@Component
class OrderProductRepositoryAdapter(
    val orderProductRepository: OrderProductRepository,
): OrderProductRepositoryPort {

    override fun createAll(orderProducts: List<OrderProductDomain>): List<OrderProductDomain> {
        return orderProductRepository.saveAll(
            orderProducts.map { OrderProductMapper.toEntity(it) }
        ).map { OrderProductMapper.toDomain(it) }
    }

    override fun findAllBy(order: OrderDomain): List<OrderProductDomain> {
        return orderProductRepository.findAllByOrder(
            OrderMapper.toEntity(order)
        ).map {
            OrderProductMapper.toDomain(it)
        }
    }

}