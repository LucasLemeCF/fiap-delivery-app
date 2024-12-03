package br.com.fiap.delivery.core.domain.exceptions

class InvalidException(
    override val message: String?,
): RuntimeException(message)