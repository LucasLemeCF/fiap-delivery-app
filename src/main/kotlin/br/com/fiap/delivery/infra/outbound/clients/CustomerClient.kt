package br.com.fiap.delivery.infra.outbound.clients

import br.com.fiap.delivery.infra.support.CustomerRequest
import br.com.fiap.delivery.infra.support.CustomerResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    value = "customerClient",
    url = "\${application.external.url.customer}",
)
interface CustomerClient {

    @GetMapping(value = ["/cliente/{cpf}"])
    fun checkCustomer(@PathVariable cpf: String): CustomerResponse

    @PostMapping(value = ["/cliente"])
    fun createCustomer(@RequestBody customer: CustomerRequest): CustomerResponse

}