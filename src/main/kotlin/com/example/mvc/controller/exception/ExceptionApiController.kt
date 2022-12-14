package com.example.mvc.controller.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import javax.validation.ConstraintViolationException
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size
import com.example.mvc.model.http.Error
import com.example.mvc.model.http.ErrorResponse
import com.example.mvc.model.http.UserRequest
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/api/exception")
@Validated
class ExceptionApiController {

    @GetMapping("/hello")
    fun hello(): String {
        val list = mutableListOf<String>()
//        val temp = list[0]
        return "hello"
    }

    // 다음과 같이 컨트롤러 내부에 Handler가 있으면, ControllerAdvice를 타지 않고 @ExceptionHandler 붙은 것을 탄다.
    @ExceptionHandler(value = [IndexOutOfBoundsException::class])
    fun indexOutOfBoundsException(e: IndexOutOfBoundsException): ResponseEntity<String> {
        println("이 경우엔 해당 Controller 안에서 일어나는 예외에 대해서만 처리 가능하다.")
        println("ControllerAdvice의 경우 스프링 내부에서 일어나는 exception을 한번에 처리할 수 있다.")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Index Error")
    }

    @GetMapping("")
    fun get(
        @NotBlank
        @Size(min = 2, max = 6)
        @RequestParam name: String,

        @Min(10)
        @RequestParam age: Int
    ): String {
        println(name)
        println(age)
        return "$name $age"
    }

    @PostMapping("")
    fun post(@Valid @RequestBody userRequest: UserRequest): UserRequest {
        println(userRequest)
        return userRequest
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun methodArgumentNotValidException(
        e: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorList = mutableListOf<Error>()

        e.bindingResult.allErrors.forEach { errorObject ->
            val errors = Error().apply {

                //		val field = it as FieldError   // 여기서 it이 objectError인데 FieldError로 형변환
                //		this.field = field.field
                //		this.message = it.defaultMessage
                //		this.value = it.

                val field = errorObject as FieldError
                this.field = field.field
                this.message = errorObject.defaultMessage
                this.value = errorObject.rejectedValue

                // 앞서 형변환을 해줬기 때문에 it으로 받는 것에 한계가 있어서 errorObject로 이름을 바꿔줌.
            }
            errorList.add(errors)
        }//forEach

        // 2. ErrorResponse 만들기
        val errorResponse = ErrorResponse().apply {
            this.resultCode = "FAIL"
            this.httpStatus = HttpStatus.BAD_REQUEST.value().toString()
            this.httpMethod = request.method
            this.message = "요처엥 에러가 발생했습니다"
            this.path = request.requestURI
            this.timestamp = LocalDateTime.now()
            this.errors = errorList
        }//apply

        //3. ResponseEntity로 반환
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun constraintViolationException(
        e: ConstraintViolationException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        // 1. 에러 분석
        val errorList = mutableListOf<Error>()

        // constraintVaiolatoins는 어떤 violation이 발생했는지 배열로 가지고 있음
        e.constraintViolations.forEach {
            val error = Error().apply {
                this.field = it.propertyPath.last().name  // propertyPath의 가장 마지막 이름이 field(변수)의 이름
                this.message = it.message
                this.value = it.invalidValue
            }//apply

            errorList.add(error)
        }//forEach

        // 2. ErrorResponse 만들기
        val errorResponse = ErrorResponse().apply {
            this.resultCode = "FAIL"
            this.httpStatus = HttpStatus.BAD_REQUEST.value().toString()
            this.httpMethod = request.method
            this.message = "요처엥 에러가 발생했습니다"
            this.path = request.requestURI
            this.timestamp = LocalDateTime.now()
            this.errors = errorList
        }//apply

        //3. ResponseEntity로 반환
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }
}