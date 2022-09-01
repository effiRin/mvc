package com.example.mvc.controller.page

import com.example.mvc.model.http.UserRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@Controller
// @RestController     -> 2개 섞어서 쓰지 말고 구분해서 쓰기!  / restAPI는 RestController, 페이징과 html 관련 컨트롤러는 @Controller
class PageController {

    // http://localhost:8080/main
    @GetMapping("/main")
    fun main(): String{         // text의 "main.html"이 나옴
        println("init main")
        return "main.html"
    }

    @ResponseBody
    @GetMapping("/test")
    fun response(): UserRequest{
        return UserRequest().apply{
            this.name = "steve"
        }
    }
}