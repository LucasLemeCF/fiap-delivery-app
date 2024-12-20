package br.com.fiap.delivery.infra.support

import br.com.fiap.delivery.core.domain.exceptions.InvalidException
import br.com.fiap.delivery.core.domain.exceptions.NotFoundException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.Exception

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [Exception::class, RuntimeException::class])
    fun defaultExceptionHandler(request: HttpServletRequest, exception: Exception): ResponseEntity<GlobalException> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                GlobalException(
                    name = exception.javaClass.simpleName,
                    message = exception.message,
                    status = HttpStatus.NOT_FOUND.name,
                )
            )
    }

    @ExceptionHandler(value = [NotFoundException::class])
    fun defaultNotFoundExceptionHandler(request: HttpServletRequest, exception: Exception): ResponseEntity<GlobalException> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(
                GlobalException(
                    name = exception.javaClass.simpleName,
                    message = exception.message,
                    status = HttpStatus.NOT_FOUND.name,
            )
        )
    }

    @ExceptionHandler(value = [InvalidException::class])
    fun defaultInvalidExceptionHandler(request: HttpServletRequest, exception: InvalidException): ResponseEntity<GlobalException> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                GlobalException(
                    name = exception.javaClass.simpleName,
                    message = exception.message,
                    status = HttpStatus.BAD_REQUEST.name,
                )
            )
    }

}