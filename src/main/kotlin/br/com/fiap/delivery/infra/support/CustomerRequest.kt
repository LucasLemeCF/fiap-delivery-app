package br.com.fiap.delivery.infra.support

data class CustomerRequest(
    val cpf: String,
    val nome: String,
    val email: String,
)
