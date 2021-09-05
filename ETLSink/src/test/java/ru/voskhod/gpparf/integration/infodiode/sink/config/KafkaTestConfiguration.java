package ru.voskhod.gpparf.integration.infodiode.sink.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.Map;
import java.util.UUID;

/**
 * Конфигурация для тестовых настроек Кафка.
 *
 * @author A.A.Dmitriev
 * @version %I%
 */
@TestConfiguration
@EmbeddedKafka
public class KafkaTestConfiguration {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    /**
     * Метод возвращает бин ConsumerFactory.
     *
     * @return бин ConsumerFactory
     */
    @Bean
    public ConsumerFactory<UUID, Object> contentConsumerFactory() {
        final Map<String, Object> consumerProps = KafkaTestUtils
                .consumerProps("waitingForAckGroup", "true", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaConsumerFactory(consumerProps, new UUIDDeserializer(),
               new StringDeserializer());
    }

    /**
     * Метод возвращает бин ConcurrentKafkaListenerContainerFactory.
     *
     * @return бин ConcurrentKafkaListenerContainerFactory
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<UUID, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<UUID, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(contentConsumerFactory());
        return factory;
    }

    /**
     * Метод возвращает бин Потребителя Kafka.
     *
     * @return бин Потребителя Kafka
     */
    @Bean
    public Consumer<UUID, Object> testConsumer() {
        ConsumerFactory<UUID, Object> consumerFactory
                = (ConsumerFactory<UUID, Object>) kafkaListenerContainerFactory().
                getConsumerFactory();
        return consumerFactory.createConsumer();
    }

}
