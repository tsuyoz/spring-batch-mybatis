package com.example.springbatchmybatis.updateage

import com.example.springbatchmybatis.da.entity.Employee
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class UpdateAgeProcessor: ItemProcessor<Employee, Employee> {
    override fun process(employee: Employee): Employee? {

        // 年齢計算
        val age = ChronoUnit.YEARS.between(employee.birthday, LocalDate.now()).toInt()

        // 変更の必要がない場合はスキップ
        if (age == employee.age) return null

        employee.age = age
        employee.updatedAt = LocalDateTime.now()
        return employee
    }
}
