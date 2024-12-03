package br.com.fiap.delivery.core.domain

import br.com.fiap.delivery.core.domain.enums.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderDomain(

    var id: Long?,
    val customer: String?,
    val creationAt: LocalDateTime = LocalDateTime.now(),
    var price: BigDecimal = BigDecimal.ZERO,
    var status: OrderStatus = OrderStatus.RECEIVED,
    var paymentCode: String? = null,

) {

    fun updatePrice(value: BigDecimal) {
        this.price = value
    }

    fun updateStatus(status: OrderStatus) {
        this.status = status
    }

    fun insertPaymentCode(code: String) {
        this.paymentCode = code
    }

}
