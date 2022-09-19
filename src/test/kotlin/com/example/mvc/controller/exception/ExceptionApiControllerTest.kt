package com.example.mvc.controller.exception

import com.example.mvc.model.http.UserRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.util.LinkedMultiValueMap

@WebMvcTest
@AutoConfigureMockMvc
class ExceptionApiControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun helloTest() {
        mockMvc.perform(    // get을 호출하기 위해서 mockMvc를 사용.
            get("/api/exception/hello")      // 해당 URI에 get메소드 실행
        ).andExpect(        // 그 다음 기대하는 값을 적어줌
            status().isOk         // 얘를 호출했을 때 200이란 값이 와야한다는 것
        ).andExpect(
            content().string("hello")     // content는 hello가 나와야한다.
        ).andDo(print())       // 이 테스트 관련 작업들을 출력해라
    }

    @Test
    fun getTest() {
        val queryParams = LinkedMultiValueMap<String, String>()
        // 쿼리 파라미터를 넘겨줘야 하므로 이렇게 선언 / key, value 모두 String

        queryParams.add("name", "rin")
        queryParams.add("age", "20")
        // key, value값 넣어주기

        mockMvc.perform(
            get("/api/exception").queryParams(queryParams)
        ).andExpect(
            status().isOk
        ).andExpect(
            content().string("rin 20")
        ).andDo(print())
    }

    @Test
    fun getFailTest() {
        val queryParams = LinkedMultiValueMap<String, String>()
        // 쿼리 파라미터를 넘겨줘야 하므로 이렇게 선언 / key, value 모두 String

        queryParams.add("name", "rin")
        queryParams.add("age", "9")
        // key, value값 넣어주기

        mockMvc.perform(
            get("/api/exception").queryParams(queryParams)

        ).andExpect(
            status().isBadRequest

        ).andExpect(
            content().contentType("application/json")
            // json 타입으로 오기 때문에 추가해줘야 함
            // 위와 동일하게 string만 주면 테스트 실패 뜸

            // json에 들어있는 내용 확인하려면 아래와 같이 쓰면 됨
        ).andExpect(
            jsonPath("\$.result_code").value("FAIL")
        ).andExpect(
            jsonPath("\$.errors[0].field").value("age")
        ).andExpect(
            jsonPath("\$.errors[0].value").value("9")

            // errors[0]에서 field(key)의 값(value)은 9가 되어야 한다

        ).andDo(print())
    }

    @Test
    fun postTest() {

        // userRequest 객체를 apply를 통해 만들어줌
        val userRequest = UserRequest().apply {
            this.name = "rin"
            this.age = 10
            this.phoneNumber = "010-0000-0000"
            this.address = "서울시 에피린구 뚜비동"
            this.email = "effirin@naver.com"
            this.createdAt = "2022-09-20 12:30:00"
        }

        //ObjectMapper를 통해서 object를 string으로 바꿔줌
        val json = jacksonObjectMapper().writeValueAsString(userRequest)
        println(json) // 확인

        // 이처럼 json을 post에서 content에 담아 보내야 하는데 너무 길기 때문에 위와 같이 만들어서 보내줌

        mockMvc.perform(
            post("/api/exception")
                .content(json)     // 패턴은 getTest과 유사하지만 post는 body에 담아보내야 함.
                .contentType("application/json")
                .accept("application/json") // json으로 받는다.
        ).andExpect(
            status().isOk
        ).andExpect(
            jsonPath("\$.name").value("rin")
        ).andExpect(
            jsonPath("\$.age").value("10")
        ).andExpect(
            jsonPath("\$.email").value("effirin@naver.com")
        ).andDo(print())
    }

    @Test
    fun postFailTest() {

        val userRequest = UserRequest().apply {
            this.name = "rin"
            this.age = -1
            this.phoneNumber = "010-0000-0000"
            this.address = "서울시 에피린구 뚜비동"
            this.email = "effirin@naver.com"
            this.createdAt = "2022-09-20 12:30:00"
        }

        val json = jacksonObjectMapper().writeValueAsString(userRequest)
        println(json) // 확인

        mockMvc.perform(
            post("/api/exception")
                .content(json)     // 패턴은 getTest과 유사하지만 post는 body에 담아보내야 함.
                .contentType("application/json")
                .accept("application/json") // json으로 받는다.
        ).andExpect(
            status().`is`(400)
        ).andDo(print())
    }
}

