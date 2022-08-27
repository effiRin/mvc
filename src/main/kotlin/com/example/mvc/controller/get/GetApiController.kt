package com.example.mvc.controller.get

import com.example.mvc.model.http.UserRequest
import org.springframework.web.bind.annotation.*

@RestController             // 해당 컨트롤러는 RestAPI로 동작할 거라는 명시
@RequestMapping("/api")    // http://localhost:8080/api  -> 해당 컨트롤러는 api라는 주소로 동작, 노출할 거란 명시
class GetApiController {

    // 최근 방식
    @GetMapping(path = ["/hello", "/abcd"])
    fun hello(): String {        // GET  http://localhost:8080/api/hello,  GET  http://localhost:8080/api/abcd
        return "hello Kotlin"
    }

    // 옛날 방식
    @RequestMapping(method = [RequestMethod.GET], path = ["/request-mapping"])
    fun requestMapping(): String {
        return "request-mapping"
    }

    @GetMapping("/get-mapping/path-variable/{name}/{age}")    // GET localhost:8080/api/get-mapping/path-variable/steve
    fun pathVariable(@PathVariable name: String, @PathVariable age: Int): String {
        println("${name}, ${age}")
        return name + " " + age
    }

    @GetMapping("/get-mapping/path-variable2/{name}/{age}")    // GET localhost:8080/api/get-mapping/path-variable/steve
    fun pathVariable2(@PathVariable(value = "name") _name: String, @PathVariable(value = "age") _age: Int): String {
        println("${_name}, ${_age}")
        return _name + " " + _age
    }

    @GetMapping("/get-mapping/query-param")     // ?name=yerin&age=18
    fun queryParam(
        @RequestParam name: String,
        @RequestParam(value = "age") _age: Int
    ): String {     // <- 리턴타입
        println("$name, $_age")                 // 인텔리제이가 이렇게 바꿔줌 - 결과는 동일한 듯?
        return "$name $_age"
    }

    // name, age, address, email
    @GetMapping("/get-mapping/query-param/object")
    fun queryParamObject(userRequest: UserRequest): UserRequest{
        println(userRequest)
        return userRequest
    }

    @GetMapping("/get-mapping/query-param/map")
    fun queryParamMap(@RequestParam map: Map<String, Any>): Map<String, Any> {
        var phoneNumber = map.get("phone-number")
        println("$map + $phoneNumber")
        return map
    }
}