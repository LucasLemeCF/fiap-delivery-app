package br.com.fiap.delivery.infra

data class GlobalException(
    val name: String,
    val message: String?,
    val status: String,
)
