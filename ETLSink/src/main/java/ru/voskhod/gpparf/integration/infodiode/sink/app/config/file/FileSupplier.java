package ru.voskhod.gpparf.integration.infodiode.sink.app.config.file;

import ru.voskhod.gpparf.integration.infodiode.sink.app.util.exception.ProcessFileException;

import java.nio.file.Path;

/**
 * Сервис предоставления свойств, необходимых для
 * чтания-записи файлов.
 *
 * @author A.Dmitriev
 * @version %I%
 */
public interface FileSupplier {

    /**
     * Метод возвращает сочетание директории, в которую записывается файл,
     * и имени данного файла.
     *
     * @param messagePacketFileName - имя файла
     * @return полное имя файла (директория + имя файла)
     * @throws ProcessFileException исключение записи файлов
     */
    Path getFullOutgoingMessageFileName(String messagePacketFileName) throws ProcessFileException;

    /**
     * Метод возвращает сочетание директории, из которой читается вложение к файлу,
     * и имени данного приложения.
     *
     * @param attachmentName имя вложения
     * @return полное имя вложения (директория + имя вложения)
     */
    Path getFullAttachmentName(String attachmentName);
}
