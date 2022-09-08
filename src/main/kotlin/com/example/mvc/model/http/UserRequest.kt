package com.example.mvc.model.http

import com.example.mvc.annotation.StringFormatDateTime
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.validation.constraints.AssertTrue
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.PositiveOrZero
import javax.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UserRequest(

    @field:NotEmpty
    @field:Size(min = 2, max = 8)
    var name: String? = null,

    @field:PositiveOrZero       // 0 포함 양수인 수
    var age: Int? = null,

    @field:Email        // 이메일 양식 검증
    var email: String? = null,

    @field:NotBlank             // 공백을 검증
    var address: String? = null,

    @field:Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}\$")      // 정규식 검증
    var phoneNumber: String? = null,

    @field:StringFormatDateTime(pattern = "yyyy-MM-dd HH:mm:ss", message = "패턴이 올바르지 않습니다.")
    var createdAt: String? = null     // yyyy-MM-dd HH:mm:ss     예) 2020-10-02 13:00:00
)
//{
//
//    @AssertTrue(message = "생성일자의 패턴은 yyyy-MM-dd HH:mm:ss여야 합니다.")
//    private fun isValidCreatedAt(): Boolean { // 정상 true, 비정상 false
//        return try {
//            LocalDateTime.parse(this.createdAt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
//            true
//        } catch (e: Exception) {
//            false
//        }
//    }
//}