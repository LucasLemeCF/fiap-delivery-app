package br.com.fiap.delivery.core.ports.outbound

import br.com.fiap.delivery.core.domain.CustomerDomain

interface CustomerPort {

    fun check(customerDomain: CustomerDomain): CustomerDomain

}