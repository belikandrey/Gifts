package com.epam.esm.controller;

import com.epam.esm.service.impl.TagService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.Mockito.*;

@Configuration
@EnableWebMvc
public class SpringTestConfiguration {
    @Bean
    public TagController tagController(){
        return new TagController(tagService());
    }

    @Bean
    public TagService tagService(){
        return mock(TagService.class);
    }
}
