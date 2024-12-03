package br.com.fiap.delivery.infra.outbound.repositories

import br.com.fiap.delivery.infra.entities.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface OrderRepository: JpaRepository<OrderEntity, Long> {

    fun findByPaymentCode(paymentCode: String): Optional<OrderEntity>

}