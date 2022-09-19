package com.example.mvc.controller.put

import com.example.mvc.model.http.Result
import com.example.mvc.model.http.UserRequest
import com.example.mvc.model.http.UserResponse
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class PutApiController {

    @PutMapping(path = ["/put-mapping"])
    fun putMapping(): String {
        return "put-mapping"
    }

    @RequestMapping(method = [RequestMethod.PUT], path = ["/request-mapping"])
    fun requestMapping(): String {
        return "request-mapping"
    }

    @PutMapping(path = ["/put-mapping/object"])
    fun putMappingObject(@Valid @RequestBody userRequest: UserRequest, bindingRequest: BindingResult): ResponseEntity<String>{

        if(bindingRequest.hasErrors()){
            //500 에러
            val msg = StringBuilder()
            bindingRequest.allErrors.forEach{
                val field = it as FieldError
                val message = it.defaultMessage
                msg.append(field.field+" : "+message+"\n")
            }
            return ResponseEntity.badRequest().body(msg.toString())
        }

        return ResponseEntity.ok("")
    }

//    @PutMapping(path = ["/put-mapping/object"])
//    fun putMappingObject(@Valid @RequestBody userRequest: UserRequest): UserResponse {
//
//        // 만들어야 할 것 - 코틀린의 표준함수 쓰는 형태로 해볼 것
//
//        // 0. UserResponse 만들기
//        return UserResponse().apply {   // apply 패턴으로 가면, userResponse가 반환될 것이고 이 안에 넣어준다.
//                                        // 이 앞에 return을 붙여주면 apply로 채워진 모든 것을 묶어서 UserResponse를 반환해준다.
//            // 1. result
//            this.result = Result().apply {                // 여기서 Result는 model.http에 있는 Result
//                this.resultCode = "OK"                     // apply 패턴으로 Result 객체를 채워준다.
//                this.resultMessage = "성공"
//            }
//        }.apply{                // UserResponse가 계속 리턴되면 됨. -> apply로 연결하면 된다.
//                                // (apply로 체이닝 - apply는 자기 자신을 계속 리턴해줌. 마지막으로 반환된 값이 아니라.)
//            // 2. description
//            this.description = "설명입니다"
//
//        }.apply{
//            // 3. user mutable list
//            val userList = mutableListOf<UserRequest>()
//
//            userList.add(userRequest)       // 우선 userRequest로 들어온 거 먼저 만들고
//
//            // 두번째, 세번째는 새로 만들겠음
//            userList.add(UserRequest().apply{
//                this.name = "tubi"
//                this.age = 1
//                this.address = "에피린동"
//                this.email = "effirin8@gmail.com"
//                this.phoneNumber = "no Phone"
//            }) // userRequest 생성자 없이 바로 apply 패턴으로 넣기
//
//            userList.add(UserRequest().apply{
//                this.name = "rinrin"
//                this.age = 10
//                this.address = "뚜비동"
//                this.email = "effirin@naver.com"
//                this.phoneNumber = "010-0000-0000"
//            })
//
//            this.user = userList     // 만든 userList를 this(UserResponse)의 userRequest에 할당한다.
//        }
//    }
}