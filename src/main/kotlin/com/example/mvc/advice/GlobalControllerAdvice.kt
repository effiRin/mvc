package com.example.mvc.advice

import com.example.mvc.controller.exception.ExceptionApiController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

//@RestControllerAdvice(basePackageClasses = [ExceptionApiController::class])   // 이 클래스에 대해서만 이 컨트롤러 어드바이스를 동작시키겠다.
class GlobalControllerAdvice {

    @ExceptionHandler(value = [RuntimeException::class])  // value - 어떤 exception class를 잡을 것인지
    fun exception(e: RuntimeException): String {        // Exception이 발생했을 때 매개변수로 넘어오게 지정
        return "Server Error"
    }

    @ExceptionHandler(value = [IndexOutOfBoundsException::class])
    fun indexOutOfBoundsException(e: IndexOutOfBoundsException): ResponseEntity<String>{
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Index Error")
    }
}