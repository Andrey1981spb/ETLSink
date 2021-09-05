package ru.voskhod.gpparf.integration.infodiode.sink.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.voskhod.gpparf.integration.infodiode.sink.app.config.file.FileSettings;
import ru.voskhod.gpparf.integration.infodiode.sink.app.config.file.FileSupplier;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Тестовый класс для работы с динамическими и статическими настройками,
 * необходимыми для чтания-записи файлов.
 *
 * @author A.Dmitriev
 * @version %I%
 */
@Component
@Profile("test")
public class TestFileSupplierImpl implements FileSupplier {

    @Autowired
    private FileSettings fileSettings;
    @Autowired
    private FileSystem fileSystem;

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
        String fullFileName = createFullFileName(directory, fileName);
        try {
            Files.createDirectories(fileSystem.getPath(directory));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Path fullJimfsFileSystemsPath = fileSystem.getPath(fullFileName);
        return fullJimfsFileSystemsPath;
    }

    private String createFullFileName(final String directory, final String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return new StringBuilder().
                    append(directory).
                    append(File.separator).
                    toString();
        } else {
            return new StringBuilder().
                    append(directory).
                    append(File.separator).
                    append(fileName).
                    toString();
        }
    }

}
