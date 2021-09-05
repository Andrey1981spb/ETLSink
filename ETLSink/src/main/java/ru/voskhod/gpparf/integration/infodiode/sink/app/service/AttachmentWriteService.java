package ru.voskhod.gpparf.integration.infodiode.sink.app.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import ru.voskhod.gpparf.integration.infodiode.sink.app.util.exception.ProcessFileException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Сервис для записи приложений к файлам.
 */
@Service
@AllArgsConstructor
@Slf4j
public class AttachmentWriteService {

    public static final String FILE_UID = "uid";

    /**
     * Метод записывает вложения к файлам.
     *
     * @param zipOutputStream поток вывода, в который записывает вложение к файлу
     * @param attachFiles     перечень файлов-вложений
     * @return поток вывода с данными вложения к файлу
     * @throws ProcessFileException вложения не могут быть записаны в исходящий поток
     */
    public ZipOutputStream writeAttachmentToZipOutputStream(final ZipOutputStream zipOutputStream,
                                                            final List<File> attachFiles)
            throws ProcessFileException {
        writeToZipOutputStreamMultiple(attachFiles, zipOutputStream);
        return zipOutputStream;
    }

    private void writeToZipOutputStreamMultiple(final List<File> attachFiles, final ZipOutputStream zipOutputStream)
            throws ProcessFileException {
        for (File attachFile : attachFiles) {
            writeToZipOutputStreamSingle(attachFile, zipOutputStream);
        }
    }

    private void writeToZipOutputStreamSingle(final File attachFile, final ZipOutputStream zipOutputStream)
            throws ProcessFileException {
        try (FileInputStream fis = new FileInputStream(attachFile)) {
            ZipEntry zipEntry = new ZipEntry(attachFile.getName());
            zipOutputStream.putNextEntry(zipEntry);
            IOUtils.copy(fis, zipOutputStream);
            zipOutputStream.closeEntry();
        } catch (IOException exception) {
            throw new ProcessFileException(exception.getMessage());
        }
    }

}
