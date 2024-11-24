package br.com.fiap.delivery.core.domain.exceptions

class NotFoundException(
    override val message: String?
): RuntimeException(message)