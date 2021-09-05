package ru.spb.voskhod.log_file;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.spb.voskhod.log_file.model.WriteFile;

@Configuration
public class FileLogger {

    private static final Logger logger = LoggerFactory.getLogger(LogFileApplication.class);

    @Bean
    public Consumer<WriteFile> logging() {
        return usageCostDetail -> {
            logger.info(usageCostDetail.toString());
        };
    }

}
