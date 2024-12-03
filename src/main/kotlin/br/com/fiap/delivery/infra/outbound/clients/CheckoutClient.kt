package br.com.fiap.delivery.infra.outbound.clients

import br.com.fiap.delivery.infra.support.CheckoutRequest
import br.com.fiap.delivery.infra.support.CheckoutResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "checkoutClient",
    url = "\${application.external.url.checkout}",
)
interface CheckoutClient {

    @PostMapping(value = ["/v1/criar_preferencia"])
    fun checkout(@RequestBody checkoutRequest: CheckoutRequest): CheckoutResponse

}