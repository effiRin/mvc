package com.example.mvc.controller.delete

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class DeleteApiController {

    // 1. request param
    @DeleteMapping("/delete-mapping")
    fun deleteMapping(
        @RequestParam(value = "name") realName: String,
        @RequestParam(value = "age") realAge: Int
    ): String {
        println("$realName $realAge")
        return "$realName $realAge"
    }

    // 2. path Variable
    @DeleteMapping(path = ["/delete-mapping/name/{name}/age/{age}"])
    fun deleteMappingPath(
        @PathVariable(value = "name") realName: String,
        @PathVariable(name = "age") realAge: Int
    ): String{
        println("$realName $realAge")
        return "$realName $realAge"
    }

}