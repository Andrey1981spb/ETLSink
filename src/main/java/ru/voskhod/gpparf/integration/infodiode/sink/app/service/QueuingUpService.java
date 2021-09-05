package ru.voskhod.gpparf.integration.infodiode.sink.app.service;

import java.util.UUID;

/**
 * Сервис для работы с очедерями.
 */
public interface QueuingUpService {

    /**
     * Метод отправляет сообщение в очередь.
     *
     * @param incomingData входящее сообщение
     * @param messageId    идентификатор сообщения
     */
    void sendMessage(String incomingData, UUID messageId);

}
