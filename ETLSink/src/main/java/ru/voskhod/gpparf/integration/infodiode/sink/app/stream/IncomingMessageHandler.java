package ru.voskhod.gpparf.integration.infodiode.sink.app.stream;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.voskhod.gpparf.integration.infodiode.sink.app.model.ContentModel;
import ru.voskhod.gpparf.integration.infodiode.sink.app.service.ContentService;
import ru.voskhod.gpparf.integration.infodiode.sink.app.service.FileService;
import ru.voskhod.gpparf.integration.infodiode.sink.app.service.impl.QueuingUpKafkaServiceImpl;
import ru.voskhod.gpparf.integration.infodiode.sink.app.util.SerializationUtils;
import ru.voskhod.gpparf.integration.infodiode.sink.app.util.exception.IncomeMessageHandleException;

import javax.validation.Validator;

/**
 * Сервис для обработки входящих сообщений.
 */
@Service
@AllArgsConstructor
@Slf4j
public class IncomingMessageHandler {

    private Validator validator;
    private FileService fileService;
    private QueuingUpKafkaServiceImpl queuingUpKafkaServiceImpl;
    private ContentService contentService;
    private SerializationUtils serializationUtils;

    /**
     * Метод получает сообщения из потока и осуществляет их дальнейшую обработку.
     *
     * @param incomingMessage входяшее сообщение
     * @throws IncomeMessageHandleException исключение обработки входящих сообщений
     */
    public void incomingMessageHandler(final String incomingMessage) throws IncomeMessageHandleException {
        log.warn("INCOMING MESSAGE " + incomingMessage);
        ContentModel contentModel;
        contentModel = serializationUtils.jSONStringToObject(incomingMessage, ContentModel.class);
        contentService.updateDateTimeMessageSend(contentModel);
        log.warn("UPDATED MESSAGE " + contentModel.toString());
        validator.validate(contentModel);
        fileService.writeFile(contentModel);
        queuingUpKafkaServiceImpl.sendMessage(incomingMessage, contentModel.getUuid());
    }
}
