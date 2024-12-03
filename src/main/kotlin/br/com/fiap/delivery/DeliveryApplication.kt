package br.com.fiap.delivery

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class DeliveryApplication

fun main(args: Array<String>) {
	runApplication<DeliveryApplication>(*args)
}
