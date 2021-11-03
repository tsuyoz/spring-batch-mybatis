package com.example.springbatchmybatis.da.entity

import java.time.LocalDate
import java.time.LocalDateTime

data class Employee(
    var id: Int,
    var name: String,
    var birthday: LocalDate,
    var age: Int,
    var updatedAt: LocalDateTime?,
)
