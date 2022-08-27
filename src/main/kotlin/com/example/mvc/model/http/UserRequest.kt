package com.example.mvc.model.http

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy :: class)
data class UserRequest (
    var name: String?=null,
    var age: Int?=null,
    var email: String?=null,
    var address: String?=null,
    var phoneNumber: String?=null
)