package br.com.fiap.delivery.infra.entities

import br.com.fiap.delivery.core.domain.enums.OrderStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity(name = OrderEntity.TABLE_NAME)
data class OrderEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDT_ORDER")
    var id: Long?,

    @Column(name = "COD_CUSTOMER")
    val customer: String? = null,

    @Column(name = "DAT_CREATION")
    val creationAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "NUM_PRICE")
    val value: BigDecimal = BigDecimal.ZERO,

    @Column(name = "DES_STATUS")
    @Enumerated(EnumType.STRING)
    val status: OrderStatus = OrderStatus.RECEIVED,

    @Column(name = "COD_EXTERNAL_PAYMENT")
    var paymentCode: String? = null,

) {

    companion object {
        const val TABLE_NAME = "ORDERS"
    }

}
