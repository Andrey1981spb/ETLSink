package ru.spb.gpparf.integration.infodiode.sink.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Служебные данные приложения.
 *
 * @author A.Dmitriev
 * @version %I%
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ServiceData implements Serializable {

    /**
     * Дата-время отправки сообщения.
     * Пример: "2021-04-20T14:55:24.462"
     */
    private LocalDateTime dateTimeMessageSend;
    /**
     * Идентификатор группы приложений.
     * Пример: закрытый контур перекладчика - zk
     */
    private String appGroupId;
    /**
     * Идентификатор процесса
     * Пример: Перекладка между контурами с помощью маршрутизатора - transferBetweenContoursUsingRouter
     */
    private String appProcessId;

    /**
     * Длительность перекладки сообщения из одного контура в другой.
     */
    private long transferDuration;

}
