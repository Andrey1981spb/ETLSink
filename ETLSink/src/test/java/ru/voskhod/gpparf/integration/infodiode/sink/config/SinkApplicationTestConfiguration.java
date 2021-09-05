/**
 * Пакет конфигурации тестов.
 */

package ru.voskhod.gpparf.integration.infodiode.sink.config;

import com.google.common.jimfs.Jimfs;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.voskhod.gpparf.integration.infodiode.sink.app.service.FileService;

import java.nio.file.FileSystem;

/**
 * Конфигурация для тестов Приемника данных.
 *
 * @author A.A.Dmitriev
 * @version %I%
 */
@Configuration
public class SinkApplicationTestConfiguration {

    /**
     * Метод создает мок для объектов класса TransferDTOToFileOutputService.
     *
     * @return мок для объекта класса
     */
    @Bean
    public FileService transferDTOToFileOutputService() {
        return Mockito.mock(FileService.class);
    }

    /**
     * Метод создает мок для тестовой файловой системы.
     *
     * @return мок для тестовой файловой системы
     */
    @Bean
    public FileSystem testFileSystem() {
        return Jimfs.newFileSystem();
    }

}

