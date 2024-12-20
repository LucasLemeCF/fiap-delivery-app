package br.com.fiap.delivery.core.domain.enums

enum class OrderStatus {
    RECEIVED,
    WAITING_PAYMENT,
    CANCELED,
    IN_PREPARATION,
    FINISHED;

    fun canFinalize(): Boolean =
        this == IN_PREPARATION
}