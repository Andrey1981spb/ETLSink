package ru.voskhod.gpparf.integration.infodiode.sink.app.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import ru.voskhod.gpparf.integration.infodiode.sink.app.config.kafka.KafkaConnectionSettings;
import ru.voskhod.gpparf.integration.infodiode.sink.app.service.QueuingUpService;

import java.text.MessageFormat;
import java.util.UUID;

/**
 * Класс для работы с очередями с использованием Apache Kafka.
 */
@Slf4j
@Service
@AllArgsConstructor
public class QueuingUpKafkaServiceImpl implements QueuingUpService {

    private KafkaTemplate<UUID, Object> kafkaTemplate;
    private KafkaConnectionSettings kafkaSettings;

    /**
     * Метод отправляет сообщение в топик Кафка по ключу.
     *
     * @param incomingData входящее сообщение
     * @param messageId    идентификатор сообщения
     */
    @Override
    public void sendMessage(final String incomingData, final UUID messageId) {
        log.debug("SEND_TO_TOPIC" + incomingData);
        try {
            ListenableFuture<SendResult<UUID, Object>> future =
                    kafkaTemplate.send(kafkaSettings.getWaitingForAckTopic(),
                            messageId, incomingData);

            future.addCallback(new ListenableFutureCallback
                    <SendResult<UUID, Object>>() {

                @Override
                public void onSuccess(final SendResult<UUID, Object> result) {
                    long offset = result.getRecordMetadata().offset();
                    log.debug("Sent message=[{}] with offset=[{}]", incomingData, offset);
                }

                @Override
                public void onFailure(final Throwable ex) {
                    log.error("Unable to send message=[{}] due to : {} ",
                            incomingData, ex);
                }
            });
        } catch (Exception exception) {
            throw new RuntimeException(MessageFormat.format("Unable to send message=[{0}] due to : ",
                    incomingData), exception);
        }
    }

}
