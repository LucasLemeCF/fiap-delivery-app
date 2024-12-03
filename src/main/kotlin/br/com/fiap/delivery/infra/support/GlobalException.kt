package br.com.fiap.delivery.infra.support

data class GlobalException(
    val name: String,
    val message: String?,
    val status: String,
)
