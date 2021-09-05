package ru.spb.gpparf.integration.infodiode.sink.app.config.kafka;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Конфигурация для работы с Производителем Кафка.
 *
 * @author A.Dmitriev
 * @version %I%
 */
@Configuration
@AllArgsConstructor
public class KafkaProducerConfig {

    private KafkaConnectionSettings kafkaSettings;

    /**
     * Метод возвращает бин производителя Кафка.
     *
     * @return бин производителя Кафка
     */
    @Bean
    public ProducerFactory<UUID, Object> configureProducer() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaSettings.getBootstrapAddress());
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                UUIDSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Метод возвращает бин KafkaTemplate.
     *
     * @return бин KafkaTemplate
     */
    @Bean
    public KafkaTemplate<UUID, Object> kafkaTemplate() {
        return new KafkaTemplate<>(configureProducer());
    }

}
