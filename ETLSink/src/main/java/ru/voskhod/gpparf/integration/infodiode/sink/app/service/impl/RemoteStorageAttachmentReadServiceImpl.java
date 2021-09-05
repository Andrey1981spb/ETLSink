package ru.voskhod.gpparf.integration.infodiode.sink.app.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.voskhod.gpparf.integration.infodiode.sink.app.model.ContentModel;
import ru.voskhod.gpparf.integration.infodiode.sink.app.service.AttachmentReadService;
import ru.voskhod.gpparf.integration.infodiode.sink.app.util.exception.ProcessFileException;

import java.io.File;
import java.util.List;

/**
 * Сервис для чтения приложений к файлам из удаленного хранилища.
 */
@AllArgsConstructor
@Service
@Profile("prod")
//TODO: подумать, как сделать профилирование по другому критерию
public class RemoteStorageAttachmentReadServiceImpl implements AttachmentReadService {

    /**
     * Читает из хранилища вложение.
     *
     * @param contentModel модель данных, содержащая информацию о вложениях
     * @return перечень вычитанных файлов-вложений
     * @throws ProcessFileException по крайнем мере один из файлов не найден в хранилище
     */
    @Override
    public List<File> readFileAttachmentsFromStorage(final ContentModel contentModel)
            throws ProcessFileException {
        //TODO реализовать
        return null;
    }

}
