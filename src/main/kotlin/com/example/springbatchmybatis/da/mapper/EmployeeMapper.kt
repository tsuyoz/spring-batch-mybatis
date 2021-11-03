package com.example.springbatchmybatis.da.mapper

import com.example.springbatchmybatis.da.entity.Employee
import org.apache.ibatis.annotations.Mapper

@Mapper
interface EmployeeMapper {
    fun selectAll(): List<Employee>
    fun updateAge(employee: Employee)
}
