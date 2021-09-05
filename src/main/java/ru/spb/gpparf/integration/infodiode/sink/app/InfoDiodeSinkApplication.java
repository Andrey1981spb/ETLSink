package ru.spb.gpparf.integration.infodiode.sink.app;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import ru.spb.gpparf.integration.infodiode.sink.app.stream.IncomingMessageHandler;
import ru.spb.gpparf.integration.infodiode.sink.app.util.metrics.CounterTransferMetrics;
import ru.spb.gpparf.integration.infodiode.sink.app.config.file.FileSettings;
import ru.spb.gpparf.integration.infodiode.sink.app.config.kafka.KafkaConnectionSettings;
import ru.spb.gpparf.integration.infodiode.sink.app.util.exception.IncomeMessageHandleException;

/**
 * Приложение для отправки данных в другой контур.
 *
 * @author A.A.Dmitriev
 * @version %I%
 */
@Slf4j
@SpringBootApplication
@EnableConfigurationProperties({FileSettings.class, KafkaConnectionSettings.class})
@EnableBinding(Sink.class)
@AllArgsConstructor
public class InfoDiodeSinkApplication {

    private IncomingMessageHandler incomingMessageHandler;

    private CounterTransferMetrics counterTransferMetrics;

    /**
     * Метод получает данные потока и передает исполнителю для их дальнейшей обработки.
     *
     * @param message incomingData сообщения
     * @throws IncomeMessageHandleException исключение обработки входящих сообщений
     */
    @StreamListener(Sink.INPUT)
    void incomingDataHandler(final Message message) throws IncomeMessageHandleException {
        String payload = message.getPayload().toString();
        incomingMessageHandler.incomingMessageHandler(payload);
        counterTransferMetrics.incrementTransferCount();
    }

    public static void main(final String[] args) {
        SpringApplication.run(InfoDiodeSinkApplication.class, args);
    }


}
