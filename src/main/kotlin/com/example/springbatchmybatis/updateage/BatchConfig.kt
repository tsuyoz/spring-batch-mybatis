package com.example.springbatchmybatis.updateage

import com.example.springbatchmybatis.da.entity.Employee
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.batch.MyBatisBatchItemWriter
import org.mybatis.spring.batch.MyBatisCursorItemReader
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableBatchProcessing
class BatchConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val sqlSessionFactory: SqlSessionFactory,
    private val updateAgeProcessor: UpdateAgeProcessor,
    private val printEmployeeTasklet: PrintEmployeeTasklet,
) {

    @Bean
    fun reader(): MyBatisCursorItemReader<Employee> {
        return MyBatisCursorItemReaderBuilder<Employee>()
            .sqlSessionFactory(sqlSessionFactory)
            .queryId("com.example.springbatchmybatis.da.mapper.EmployeeMapper.selectAll")
            .build()
    }

    @Bean
    fun writer(): MyBatisBatchItemWriter<Employee> {
        return MyBatisBatchItemWriterBuilder<Employee>()
            .sqlSessionFactory(sqlSessionFactory)
            .statementId("com.example.springbatchmybatis.da.mapper.EmployeeMapper.updateAge")
            .build()
    }

    @Bean
    fun updateStep(): Step {
        return stepBuilderFactory.get("update-age-update-step")
            .chunk<Employee, Employee>(10)
            .reader(reader())
            .processor(updateAgeProcessor)
            .writer(writer())
            .build()
    }

    @Bean
    fun printStep(): Step {
        return stepBuilderFactory.get("update-age-print-step")
            .tasklet(printEmployeeTasklet)
            .build()
    }

    @Bean
    fun job(): Job {
        return jobBuilderFactory.get("update-age-job")
            .start(updateStep())
            .next(printStep())
            .build()
    }
}
