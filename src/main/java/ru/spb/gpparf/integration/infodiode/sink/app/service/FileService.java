package ru.spb.gpparf.integration.infodiode.sink.app.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.spb.gpparf.integration.infodiode.sink.app.model.ContentModel;
import ru.spb.gpparf.integration.infodiode.sink.app.util.SerializationUtils;
import ru.spb.gpparf.integration.infodiode.sink.app.config.file.FileSupplier;
import ru.spb.gpparf.integration.infodiode.sink.app.util.exception.IncomeMessageHandleException;
import ru.spb.gpparf.integration.infodiode.sink.app.util.exception.ProcessFileException;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Преобразует dto в файлы и записывает в определенную директорию.
 *
 * @author A.A.Dmitriev
 * @version %I%
 */
@Service
@Slf4j
@AllArgsConstructor
public class FileService {

    private AttachmentReadService attachmentReadService;
    private AttachmentWriteService attachmentService;
    private SerializationUtils serializationUtils;

    private FileSupplier fileSupplier;
    public static final String ZIP_FILE_EXTENSION = ".zip";
    private static final Integer START_DISPATCH_ACCOUNT = 1;
    public static final String FILE_NAME_SEPARATOR = "_";
    public static final String MESSAGE_FILE_NAME = "message.json";

    /**
     * Метод преобразует данные в файлы и записывает в назначенную директорию.
     *
     * @param contentModel - данные для преобразования в файл
     * @throws IncomeMessageHandleException исключение чтения-записи файлов при обработке входящих сообщений
     */
    public void writeFile(final ContentModel contentModel) throws IncomeMessageHandleException {
        UUID messageId = contentModel.getUuid();
        Path messageFileName = getFullOutgoingMessageFileName(messageId, contentModel.getNumberOfDispatches());
        transferToZipOutput(messageFileName, contentModel);
    }

    private void transferToZipOutput(final Path fullMessageFileName, final ContentModel contentModel)
            throws IncomeMessageHandleException {
        ZipOutputStream zos = null;
        try {
            List<File> attachFiles = attachmentReadService.readFileAttachmentsFromStorage(contentModel);
            zos = createZipOutputStream(fullMessageFileName);
            attachmentService.writeAttachmentToZipOutputStream(zos, attachFiles);
            String messageFileName = getMessageFileName();
            transferMessageToZipOutputStream(messageFileName, contentModel, zos);
        } catch (ProcessFileException exception) {
            throw new IncomeMessageHandleException(
                    MessageFormat.format("Ошибка при записи файла с полным именем {0} ",
                            fullMessageFileName), exception);
        } finally {
            closeZipInputStream(zos);
        }
    }

    private ZipOutputStream createZipOutputStream(final Path fullMessageFileName) throws ProcessFileException {
        try {
            OutputStream outputStream = Files.newOutputStream(fullMessageFileName);
            return new ZipOutputStream(outputStream);
        } catch (IOException exception) {
            throw new ProcessFileException(
                    MessageFormat.format("Ошибка создания исходящего потока для файлу с полным именем {0} ",
                            fullMessageFileName.toString()), exception);
        }
    }

    private void transferMessageToZipOutputStream(final String messageFileName, final ContentModel contentModel,
                                                  final ZipOutputStream zos) throws ProcessFileException {
        try {
            ZipEntry zipEntry = new ZipEntry(getMessageFileName());
            zos.putNextEntry(zipEntry);
            zos.write(serializationUtils.jSONObjectToString(contentModel).getBytes(StandardCharsets.UTF_8));
            zos.closeEntry();
        } catch (IOException exception) {
            throw new ProcessFileException(
                    MessageFormat.format("Ошибка при записи файла с полным именем {0} ",
                            messageFileName), exception);
        }
    }

    private Path getFullOutgoingMessageFileName(final UUID messageId,
                                                final Integer numberOfDispatches) throws ProcessFileException {
        String messagePacketFileName = getMessagePacketFileName(messageId, numberOfDispatches);
        try {
            return fileSupplier.getFullOutgoingMessageFileName(messagePacketFileName);
        } catch (RuntimeException exception) {
            throw new ProcessFileException(
                    MessageFormat.format("Ошибка получения имени файла для {0}", messageId), exception);
        }
    }

    private String getMessagePacketFileName(final UUID messageId, final Integer numberOfDispatches) {
        int incrementedNumberOfDispatches = numberOfDispatches == null ? START_DISPATCH_ACCOUNT : numberOfDispatches;
        return new StringBuilder().
                append(messageId).
                append(FILE_NAME_SEPARATOR).
                append(incrementedNumberOfDispatches).
                append(ZIP_FILE_EXTENSION).
                toString();
    }

    private String getMessageFileName() {
        return MESSAGE_FILE_NAME;
    }

    private void closeZipInputStream(final ZipOutputStream zos) throws IncomeMessageHandleException {
        if (zos == null) {
            return;
        }
        try {
            zos.close();
        } catch (IOException exception) {
            throw new IncomeMessageHandleException("Ошибка закрытия исходящего потока",
                    exception);
        }
    }

}
