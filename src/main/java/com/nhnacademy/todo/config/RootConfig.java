package com.nhnacademy.todo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nhnacademy.todo.repository.EventRepository;
import com.nhnacademy.todo.repository.impl.MemoryEventRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;


@Configuration
@ComponentScan(basePackageClasses = {com.nhnacademy.todo.Base.class}, excludeFilters = {@ComponentScan.Filter(classes = {Controller.class})})
public class RootConfig {

    @Bean(name = "objectMapper")
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        //pretty print json
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        //value -> null 무시
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //LocalDate, LocalDateTime support jsr310
        objectMapper.registerModule(new JavaTimeModule());
        //timestamp 출력을 disable. -> 문자열형태로 출력
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }
    @Bean(name="xmlMapper")
    public XmlMapper xmlMapper(){
        XmlMapper xmlMapper = new XmlMapper();
        //pretty print xml
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        //value -> null 무시
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //LocalDate, LocalDateTime support jsr310
        xmlMapper.registerModule(new JavaTimeModule());
        //timestamp 출력을 disable. -> 문자열형태로 출력
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return xmlMapper;
    }

    @Bean(initMethod = "init")
    public EventRepository eventRepository(){
       return new MemoryEventRepository();
    }
}
