package com.example.mvc.controller.delete

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Validated
@RestController
@RequestMapping("/api")
class DeleteApiController {

    // 1. request param
    @DeleteMapping("/delete-mapping")
    fun deleteMapping(
        @RequestParam(value = "name") realName: String,

        @NotNull(message = "age값이 누락되었습니다.")
        @Min(value = 20, message = "age는 20보다 커야 합니다.")
        @RequestParam(value = "age") realAge: Int
    ): String {
        println("$realName $realAge")
        return "$realName $realAge"
    }

    // 2. path Variable
    @DeleteMapping(path = ["/delete-mapping/name/{name}/age/{age}"])
    fun deleteMappingPath(

        @Size (min = 2, max = 5, message = "name은 2~5글자 사이여야 합니다.")
        @NotNull
        @PathVariable(value = "name") realName: String,

        @NotNull(message = "age값이 누락되었습니다.")
        @Min(value = 20, message = "age는 20보다 커야 합니다.")
        @PathVariable(name = "age") realAge: Int
    ): String{
        println("$realName $realAge")
        return "$realName $realAge"
    }

}