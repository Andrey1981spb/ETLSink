package ru.spb.gpparf.integration.infodiode.sink.app.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.spb.gpparf.integration.infodiode.sink.app.model.ContentModel;
import ru.spb.gpparf.integration.infodiode.sink.app.service.AttachmentReadService;
import ru.spb.gpparf.integration.infodiode.sink.app.service.AttachmentWriteService;
import ru.spb.gpparf.integration.infodiode.sink.app.util.SerializationUtils;
import ru.spb.gpparf.integration.infodiode.sink.app.util.exception.ProcessFileException;
import ru.spb.gpparf.integration.infodiode.sink.app.config.file.FileSupplier;
import ru.spb.gpparf.integration.infodiode.sink.app.util.exception.ReadFileException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Сервис для чтения приложений к файлам из локальной директории.
 */
@Slf4j
@AllArgsConstructor
@Service
@Profile({"test", "app"})
//TODO: подумать, как сделать профилирование по другому критерию
public class LocalStorageAttachmentReadServiceImpl implements AttachmentReadService {

    private FileSupplier fileSupplier;
    private static final String UID_SPLITTER = ";";
    private SerializationUtils serializationUtils;

    /**
     * Метод читает вложения из локальной директории.
     *
     * @param contentModel модель данных, содержащая информацию о вложениях
     * @return перечень вычитанных файлов
     * @throws ProcessFileException по крайнем мере один из файлов не найден в хранилище
     */
    @Override
    public List<File> readFileAttachmentsFromStorage(final ContentModel contentModel)
            throws ProcessFileException {
        List<File> filesList = new ArrayList<>();
        try {
            List<String> attachmentNames = getAttachmentNames(contentModel);
            if (isAttachmentNamesEmpty(attachmentNames)) {
                return filesList;
            }
            filesList = readFileAttachmentsFromStorage(attachmentNames, filesList);
        } catch (ReadFileException exception) {
            throw new ProcessFileException(exception.getMessage());
        }
        return filesList;
    }

    private List<String> getAttachmentNames(final ContentModel contentModel) throws ReadFileException {
        String attachmentIds = getAttachmentIds(contentModel);
        return Arrays.asList(attachmentIds.split(UID_SPLITTER));
    }

    private String getAttachmentIds(final ContentModel contentModel) throws ReadFileException {
        String payload = contentModel.getPayload();
        JsonNode payloadNode = serializationUtils.jSONStringToObject(payload, JsonNode.class);
        JsonNode uidNode = payloadNode.get(AttachmentWriteService.FILE_UID);
        return uidNode.textValue();
    }

    private boolean isAttachmentNamesEmpty(final List<String> attachmentNames) {
        if (attachmentNames == null) {
            return true;
        }
        for (String attachmentName : attachmentNames) {
            if (StringUtils.isEmpty(attachmentName)) {
                return true;
            }
        }
        return false;
    }

    private List<File> readFileAttachmentsFromStorage(final List<String> attachmentNames, final List<File> filesList)
            throws ReadFileException {
        for (String attachmentName : attachmentNames) {
            String fullAttachmentName = fileSupplier.getFullAttachmentName(attachmentName).toString();
            File file = new File(fullAttachmentName);
            this.checkFileExist(file);
            filesList.add(file);
        }
        return filesList;
    }

}
