package br.com.fiap.delivery.core.domain.enums

enum class OrderStatus {
    RECEIVED,
    WAITING_PAYMENT,
    PAID,
    CANCELED,
    IN_PREPARATION,
    FINISHED,
}