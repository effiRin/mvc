package com.example.mvc.controller.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/exception")
class ExceptionApiController {

    @GetMapping("/hello")
    fun hello(){
        val list = mutableListOf<String>()
        val temp = list[0]
    }

    // 다음과 같이 컨트롤러 내부에 Handler가 있으면, ControllerAdvice를 타지 않고 @ExceptionHandler 붙은 것을 탄다.
    @ExceptionHandler(value = [IndexOutOfBoundsException::class])
    fun indexOutOfBoundsException(e: IndexOutOfBoundsException): ResponseEntity<String> {
        println("이 경우엔 해당 Controller 안에서 일어나는 예외에 대해서만 처리 가능하다.")
        println("ControllerAdvice의 경우 스프링 내부에서 일어나는 exception을 한번에 처리할 수 있다.")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Index Error")
    }
}