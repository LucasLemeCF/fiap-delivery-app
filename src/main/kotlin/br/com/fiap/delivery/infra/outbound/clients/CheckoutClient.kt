package br.com.fiap.delivery.infra.outbound.clients

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.math.BigDecimal

@FeignClient(
    name = "checkoutClient",
    url = "\${application.external.url.checkout}",
)
interface CheckoutClient {

    @GetMapping(value = ["/checkout"])
    fun checkout(@RequestParam value: BigDecimal): Boolean

}