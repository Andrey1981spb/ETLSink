package ru.spb.voskhod.file_read_maven;

import com.google.gson.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import ru.spb.voskhod.file_read_maven.model.ReadFile;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.function.Supplier;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
public class FileDataSender {

    @Value("classpath:/budget.txt")
    Resource resourceFile;

    @Bean
    public Supplier<ReadFile> sendFileData() {

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, (JsonDeserializer<org.joda.time.LocalDate>) (json, typeOfT, context)
                -> new org.joda.time.LocalDate(json.getAsString())).setLenient().
                create();

        return () -> {
            ReadFile readFile;
            String fileStringContent = asString(resourceFile);

            JsonParser parser = new JsonParser();
            JsonObject fileJSONContent = parser.parse(fileStringContent).getAsJsonObject();

            readFile = gson.fromJson(fileJSONContent, ReadFile.class);
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
