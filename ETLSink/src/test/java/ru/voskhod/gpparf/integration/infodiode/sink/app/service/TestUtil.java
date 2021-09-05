package ru.voskhod.gpparf.integration.infodiode.sink.app.service;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.voskhod.gpparf.integration.infodiode.sink.app.config.file.FileSupplier;

import java.io.File;
import java.util.Arrays;

/**
 * Утильный сервис для работы с тестовым окружением.
 *
 * @author A.A.Dmitriev
 * @version %I%
 */
@Component
public class TestUtil {

    @Autowired
    private FileSupplier fileSupplier;
    private static final String TEST_FILE_SYSTEM_ID = "com.google.common.jimfs";

    //TODO реализовать при решении проблем с фэйковой файловой системой

    //  /**
    //   * Метод создает в тестовой директории тестовое вложение к файлу.
    //   *
    // * @throws ReadFileException исключение чтения файлов
    //  * @throws WriteFileException исключение записии файлов
    //  */
    // public void writeTestFileAttachment() throws ReadFileException, WriteFileException {
    //     Path testDirectory = fileSupplier.getFullAttachmentName(ATTACHMENT_ID);
    //    try {
    //        new FileOutputStream(String.valueOf(Files.newOutputStream(testDirectory)));
    //    } catch (IOException exception) {
    //       throw new WriteFileException(
    //              MessageFormat.format("Ошибка записи вложения в тестовую директорию {0} по причине ",
    //                       testDirectory), exception);
    //   }
    //  }

    /**
     * Метод очищает директорию проекта от файлов тестовой файловой системы.
     */
    public void clearTestDirectory() {
        File projectDir = new File("").getAbsoluteFile();
        File[] files = projectDir.listFiles();
        Arrays.stream(files).
                filter(file -> file.getName().startsWith(TEST_FILE_SYSTEM_ID)).
                forEach(file -> FileUtils.deleteQuietly(file));
    }

}
