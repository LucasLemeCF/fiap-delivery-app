package br.com.fiap.delivery.adapters.outbound

import br.com.fiap.delivery.core.ports.outbound.CustomerPort
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CustomerAdapter: CustomerPort {

    override fun check(customer: String) {
        logger.info("check")
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(CustomerAdapter::class.java)
    }

}