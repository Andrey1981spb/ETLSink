package ru.voskhod.gpparf.integration.infodiode.sink.app.config.file;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

/**
 * Динамические и статические настройки,
 * необходимые для чтания-записи файлов.
 *
 * @author A.Dmitriev
 * @version %I%
 */
@Data
@ConfigurationProperties("app")
public class FileSettings {

    /**
     * Элемент пути, монтируемый к пути в docker-контейнере.
     */
    private String dockerMountPath = "/root/scdf";
    /**
     * Элемент пути, предшествуюеший элементу,
     * отражающему сторону (открытый-закрытый контрур).
     */
    private String directoryToSideName = "infodiod";
    /**
     * Элемент пути,отражающий сторону
     * (открытый-закрытый контур).
     */
    private SideName sideName = SideName.valueOf("PUBLIC");
    /**
     * Элемент пути, отражающий направление записи
     * файлов в другой контур.
     */
    private String outbox = "outbox";
    /**
     * Элемент пути, указывающий на папку, в которой
     * хранятся вложения к файлам.
     */
    private String attachments = "attachments";

    /**
     * Метод возвращает строковое представление директории, в которую записывается файл.
     *
     * @return директория, в которую записывается файл
     */
    public String createOutgoingDirectoryToFileAsString() {
        String outgoingDirectory = new StringBuilder().
                append(dockerMountPath).
                append(File.separator).
                append(directoryToSideName).
                append(File.separator).
                append(sideName.toString().toLowerCase()).
                append(File.separator).
                append(outbox).
                toString();
        return outgoingDirectory;
    }

    /**
     * Метод возвращает строковое представление директории
     * вложений к файлам. К примеру "/root/scdf/attachments".
     *
     * @return директория, в которую записывается файл
     */
    public String getAttachmentDirectory() {
        String attachmentDirectory = new StringBuilder().
                append(dockerMountPath).
                append(File.separator).
                append(attachments).
                toString();
        return attachmentDirectory;
    }

}
