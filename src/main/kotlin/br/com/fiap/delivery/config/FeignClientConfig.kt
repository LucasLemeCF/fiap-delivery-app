package br.com.fiap.delivery.config

import feign.Client
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignClientConfig {

    @Bean
    fun defaultFeignClient(): Client {
        return Client.Default(null, null)
    }

}