package ru.voskhod.gpparf.integration.infodiode.sink.app.service;

import ru.voskhod.gpparf.integration.infodiode.sink.app.model.ContentModel;
import ru.voskhod.gpparf.integration.infodiode.sink.app.util.exception.ReadFileException;
import ru.voskhod.gpparf.integration.infodiode.sink.app.util.exception.ProcessFileException;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

/**
 * Сервис для чтения приложений к файлам.
 */
public interface AttachmentReadService {

    /**
     * Читает из хранилища прикрепленный файл.
     *
     * @param contentModel модель данных, содержащая информацию о вложенияx
     * @return перечень вычитанных файлов
     * @throws ProcessFileException по крайнем мере один из файлов не найден в хранилище
     */
    List<File> readFileAttachmentsFromStorage(ContentModel contentModel)
            throws ProcessFileException;
    /**
     * Метод проверяет существование файла с определенным именем.
     *
     * @param attachmentFile искомый файл-вложение
     * @throws ReadFileException исключение отсутствия вложения с определенным именем
     */
    default void checkFileExist(File attachmentFile) throws ReadFileException {
        if (!attachmentFile.exists() || attachmentFile.isDirectory()) {
            throw new ReadFileException(
                    MessageFormat.format("Вложение с именем {0} не существует ",
                            attachmentFile.getName()));
        }
    }

}
