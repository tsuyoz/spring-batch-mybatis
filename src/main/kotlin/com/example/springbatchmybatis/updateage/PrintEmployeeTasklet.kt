package com.example.springbatchmybatis.updateage

import com.example.springbatchmybatis.da.mapper.EmployeeMapper
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Service

@Service
class PrintEmployeeTasklet(
    private val employeeMapper: EmployeeMapper,
): Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        val employees = employeeMapper.selectAll()

        employees.forEach { emp ->
            println("[ID]${emp.id} [NAME]${emp.name} [BIRTHDAY]${emp.birthday} [AGE]${emp.age}")
        }

        return RepeatStatus.FINISHED
    }
}
