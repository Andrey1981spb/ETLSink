package ru.voskhod.gpparf.integration.infodiode.sink.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Общая конфигурация приложения.
 *
 * @author A.Dmitriev
 * @version %I%
 */
@Configuration
public class ApplicationConfig {

    /**
     * Метод возвращает бин ObjectMapper.
     *
     * @return бин ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    /**
     * Метод возвращает бин валидатора.
     *
     * @return валидатор
     */
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        return validatorFactory.usingContext().getValidator();
    }

}
