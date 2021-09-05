package ru.voskhod.gpparf.integration.infodiode.sink.app.config.kafka;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Конфигурация настроек для работы с Кафка.
 *
 * @author A.Dmitriev
 * @version %I%
 */
@Data
@ConfigurationProperties("kafka")
public class KafkaConnectionSettings {
    /**
     * Адрес брокера Кафка.
     */
    @Value("${spring.cloud.stream.kafka.binder.brokers}")
    private String bootstrapAddress;
    /**
     * Топик сообщений, доставка которых не подтверждена.
     */
    private String waitingForAckTopic = "waiting_for_ack";

}
