package ru.voskhod.gpparf.integration.infodiode.sink.app.config.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Сервис для работы с динамическими и статическими настройками,
 * необходимыми для чтания-записи файлов.
 *
 * @author A.Dmitriev
 * @version %I%
 */
@Component
@Profile("app")
public class AppFileSupplierImpl implements FileSupplier {

    @Autowired
    private FileSettings fileSettings;

    @Override
    public Path getFullOutgoingMessageFileName(final String messagePacketFileName) {
        String outgoingDirectory = fileSettings.createOutgoingDirectoryToFileAsString();
        return getFullFileNameAsPath(outgoingDirectory, messagePacketFileName);
    }

    @Override
    public Path getFullAttachmentName(final String attachmentName) {
        String attachmentDirectory = fileSettings.getAttachmentDirectory();
        return getFullFileNameAsPath(attachmentDirectory, attachmentName);
    }

    private Path getFullFileNameAsPath(final String directory, final String fileName) {
        String fullFileName = new StringBuilder().
                append(directory).
                append(File.separator).
                append(fileName).
                toString();
        return Paths.get(fullFileName);
    }

}
