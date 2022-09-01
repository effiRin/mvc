package com.example.mvc.controller.response

import com.example.mvc.model.http.UserRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/response")
class ResponseApiController {

    // 1. get 4xx
    // GET http://localhost:8080/api/response?age=10

    @GetMapping
    fun getMapping(@RequestParam age : Int?): ResponseEntity<String> {

        /** 코틀린스럽게 코딩하기 - 엘비스 연산자 이용
         1. age == null -> 400 error
         */

        return age?.let{

            if(it < 20){
                return ResponseEntity.status(400).body("age 값은 20보다 커야 합니다.")
            }
            ResponseEntity.ok("OK")
        }?: kotlin.run{
            return ResponseEntity.status(400).body("age 값이 누락되었습니다.")
        }


//        if(age == null){
//            return ResponseEntity.status(400).body("age값이 누락되었습니다.")
//        }
//
//        if(age < 20){
//            return ResponseEntity.status(400).body("Fail")
//        }
//
//        return ResponseEntity.ok("OK")
    }

    // 2. post 200
    @PostMapping("")
    fun postMapping(@RequestBody userRequest: UserRequest?): ResponseEntity<Any>{
        return ResponseEntity.status(200).body(userRequest)     // object mapper -> object -> json
    }

    // 3. put 201
    @PutMapping("")
    fun putMapping(@RequestBody userRequest: UserRequest?): ResponseEntity<UserRequest>{
        // 1. 기존 데이터가 없어서 새로 생성했다.
        return ResponseEntity.status(HttpStatus.CREATED).body(userRequest)
    }

    @DeleteMapping("/{id}")
    fun deleteMapping(@PathVariable id:Int): ResponseEntity<Any>{
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
    }
}