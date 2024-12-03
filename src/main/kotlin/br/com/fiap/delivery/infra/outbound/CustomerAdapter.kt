package br.com.fiap.delivery.infra.outbound

import br.com.fiap.delivery.core.domain.CustomerDomain
import br.com.fiap.delivery.core.domain.exceptions.InvalidException
import br.com.fiap.delivery.core.ports.outbound.CustomerPort
import br.com.fiap.delivery.infra.mappers.CustomerMapper
import br.com.fiap.delivery.infra.outbound.clients.CustomerClient
import feign.FeignException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CustomerAdapter(
    val customerClient: CustomerClient
): CustomerPort {

    override fun check(customerDomain: CustomerDomain): CustomerDomain {
        return kotlin.runCatching {
            customerClient.checkCustomer(customerDomain.cpf)
        }.fold(
            onSuccess = {
                logger.info("Success: Customer check completed for customer=$customerDomain")
                CustomerMapper.toDomain(it)
            },
            onFailure = { exception ->
                when (exception) {
                    is FeignException.NotFound -> {
                        logger.warn("Customer not found: Attempting to create customer=$customerDomain")
                        createCustomer(customerDomain)
                    }
                    is FeignException -> {
                        logger.error("Error during customer check: ${exception.message}", exception)
                        throw InvalidException("Error while checking customer: ${exception.message}")
                    }
                    else -> {
                        logger.error("Unexpected error: ${exception.message}", exception)
                        throw exception
                    }
                }
            }
        )
    }

    private fun createCustomer(customerDomain: CustomerDomain): CustomerDomain {
        return kotlin.runCatching {
            validateCustomerData(customerDomain)
            CustomerMapper.toDomain(
                customerClient.createCustomer(
                    CustomerMapper.toRequest(customerDomain)
                )
            )
        }.fold(
            onSuccess = {
                logger.info("Success: Customer created for customer=$customerDomain")
                it
            },
            onFailure = {
                logger.error("Error during customer creation: ${it.message}", it)
                throw InvalidException("Failed to create customer: ${it.message}")
            }
        )
    }

    private fun validateCustomerData(customerDomain: CustomerDomain) {
        if (customerDomain.name == null || customerDomain.email == null) {
            throw InvalidException("Customer data is incomplete: $customerDomain")
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(CustomerAdapter::class.java)
    }

}