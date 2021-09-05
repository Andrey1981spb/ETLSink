package ru.spb.gpparf.integration.infodiode.sink.app.service;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.spb.gpparf.integration.infodiode.sink.app.TestData;
import ru.spb.gpparf.integration.infodiode.sink.app.config.ApplicationConfig;
import ru.spb.gpparf.integration.infodiode.sink.app.config.file.AppFileSupplierImpl;
import ru.spb.gpparf.integration.infodiode.sink.app.config.kafka.KafkaProducerConfig;
import ru.spb.gpparf.integration.infodiode.sink.app.service.impl.QueuingUpKafkaServiceImpl;
import ru.spb.gpparf.integration.infodiode.sink.app.util.SerializationUtils;
import ru.spb.gpparf.integration.infodiode.sink.app.util.exception.ProcessFileException;
import ru.spb.gpparf.integration.infodiode.sink.app.config.kafka.KafkaConnectionSettings;
import ru.voskhod.gpparf.integration.infodiode.sink.config.KafkaTestConfiguration;

import java.time.Duration;
import java.util.UUID;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasKey;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasValue;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Import({KafkaTestConfiguration.class})
@EnableConfigurationProperties({KafkaConnectionSettings.class})
@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
        classes = {KafkaProducerConfig.class, QueuingUpKafkaServiceImpl.class, TestData.class,
                ApplicationConfig.class, AppFileSupplierImpl.class, SerializationUtils.class})
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {"waiting_for_ack"})
public class QueuingUpKafkaServiceTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;
    @Autowired
    private Consumer<UUID, Object> testConsumer;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private QueuingUpKafkaServiceImpl queuingUpKafkaServiceImpl;
    @Autowired
    private TestData testData;
    @Autowired
    private KafkaConnectionSettings connectionSettings;

    private static final long TIME_OUT = 1000;

    /**
     * Метод тестирует контекст Spring.
     */
    public void contextLoads() {
        assertNotNull(this.applicationContext);
    }

    @Test
    public void testSendMessageToKafkaTopic() throws ProcessFileException {
        String incomeJson = testData.createTestIncomeJson();
        UUID messageId = testData.getUuid();

        queuingUpKafkaServiceImpl.sendMessage(incomeJson, messageId);
        embeddedKafkaBroker.consumeFromEmbeddedTopics(testConsumer, connectionSettings.getWaitingForAckTopic());
        ConsumerRecord<UUID, Object> record = getSingleRecord(testConsumer, TIME_OUT);

        assertThat(record, hasKey(messageId));
        assertThat(record, hasValue(incomeJson));
    }

    private ConsumerRecord<UUID, Object> getSingleRecord(final Consumer<UUID, Object> consumer,
                                                         final long timeout) {
        ConsumerRecords<UUID, Object> records = consumer.poll(Duration.ofMillis(timeout));
        return records.iterator().next();
    }

}
