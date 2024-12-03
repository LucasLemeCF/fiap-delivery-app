package br.com.fiap.delivery.infra.mappers

import br.com.fiap.delivery.core.domain.CustomerDomain
import br.com.fiap.delivery.infra.support.CustomerRequest
import br.com.fiap.delivery.infra.support.CustomerResponse

object CustomerMapper {

    fun toRequest(customerDomain: CustomerDomain): CustomerRequest {
        return CustomerRequest(
            cpf = customerDomain.cpf,
            nome = customerDomain.name!!,
            email = customerDomain.email!!,
        )
    }

    fun toDomain(customerResponse: CustomerResponse): CustomerDomain {
        return CustomerDomain(
            cpf = customerResponse.cpf,
            name = customerResponse.nome,
            email = customerResponse.email,
        )
    }

}