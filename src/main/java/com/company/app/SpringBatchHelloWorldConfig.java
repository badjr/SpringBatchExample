package com.company.app;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import sun.rmi.runtime.Log;

/**
 * Created by Brett on 1/23/2017.
 */
@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
public class SpringBatchHelloWorldConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

//    private static Log LOGGER = LogFactory.getLog(SpringBatchHelloWorldConfig.class);

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Employee, Employee>chunk(2)
                .reader(employeeItemReader())
                .processor(employeeItemProcessor())
                .writer(employeeItemWriter())
                .build();
    }

    @Bean
    public Job listEmployeesJob(Step step1) throws Exception {
        return jobBuilderFactory.get("listEmployeesJob")
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }

    //    @Bean
//    ItemReader<Employee> employeeItemReader() {
//        FlatFileItemReader<Employee> reader = new FlatFileItemReader<>();
//        reader.setResource(new ClassPathResource("employees.csv"));
//
//        DefaultLineMapper defaultLineMapper = new DefaultLineMapper();
//        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
//        delimitedLineTokenizer.setNames(new String[] {"firstName", "lastName", "age", "salary"});
//
//        BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
//        fieldSetMapper.setTargetType(Employee.class);
//
//        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
//        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
//        reader.setLineMapper(defaultLineMapper);
//
//        return reader;
//    }
    @Bean
    ItemReader<Employee> employeeItemReader() {
        return new EmployeeItemReader<>("employees.csv");
    }

    @Bean
    ItemProcessor<Employee, Employee> employeeItemProcessor() {
        return new EmployeeItemProcessor();
    }

    @Bean
    ItemWriter<Employee> employeeItemWriter() {
        return new EmployeeItemWriter();
    }


}