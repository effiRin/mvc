package com.example.mvc.controller.post

import com.example.mvc.model.http.UserRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class PostApiController     {

    //최근 방법
    @PostMapping("/post-mapping")
    fun postMapping(): String{
        return "Post Mapping"
    }

    //예전 방법
    @RequestMapping(method = [RequestMethod.POST], path = ["/request-mapping"])
    fun requestMapping(): String{
        return "request-mapping"
    }

    // object mapper (json <-> object)
    @PostMapping("/post-mapping/object")
    fun postMappingObject(@RequestBody userRequest: UserRequest): UserRequest{
        // 들어올 때는 json -> object로 바꿔서 들어옴
        println(userRequest)
        return userRequest  // return 시킬 때는 object ->  json으로 바꿔서 나간다.
    }
}