package ru.spb.voskhod.file_read;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import ru.spb.voskhod.file_read.model.ReadFile;

import java.io.*;
import java.util.function.Supplier;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
public class FileDataSender {

    @Value("classpath:/home/andrej/dataflow/file_read/budget.txt")
    Resource resourceFile;

    @Bean
    public Supplier<ReadFile> sendFileData() {
        return () -> {
            ReadFile readFile;
            String fileStringContent = asString(resourceFile);
            readFile = new Gson().fromJson(fileStringContent, ReadFile.class);
            return readFile;
        };
    }

    private String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


}
