package ru.voskhod.gpparf.integration.infodiode.sink.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.voskhod.gpparf.integration.infodiode.sink.app.config.file.FileSettings;
import ru.voskhod.gpparf.integration.infodiode.sink.app.config.file.FileSupplier;
import ru.voskhod.gpparf.integration.infodiode.sink.app.config.kafka.KafkaConnectionSettings;
import ru.voskhod.gpparf.integration.infodiode.sink.app.config.kafka.KafkaProducerConfig;
import ru.voskhod.gpparf.integration.infodiode.sink.app.service.AttachmentReadService;
import ru.voskhod.gpparf.integration.infodiode.sink.app.service.AttachmentWriteService;
import ru.voskhod.gpparf.integration.infodiode.sink.app.service.ContentService;
import ru.voskhod.gpparf.integration.infodiode.sink.app.service.FileService;
import ru.voskhod.gpparf.integration.infodiode.sink.app.service.impl.LocalStorageAttachmentReadServiceImpl;
import ru.voskhod.gpparf.integration.infodiode.sink.app.service.impl.QueuingUpKafkaServiceImpl;
import ru.voskhod.gpparf.integration.infodiode.sink.app.util.SerializationUtils;
import ru.voskhod.gpparf.integration.infodiode.sink.config.SinkApplicationTestConfiguration;
import ru.voskhod.gpparf.integration.infodiode.sink.config.TestFileSupplierImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FileSettings.class, FileSupplier.class, FileService.class,
        QueuingUpKafkaServiceImpl.class, TestFileSupplierImpl.class,
        SinkApplicationTestConfiguration.class, KafkaProducerConfig.class,
        KafkaConnectionSettings.class, AttachmentWriteService.class, AttachmentReadService.class,
        LocalStorageAttachmentReadServiceImpl.class, ContentService.class, SerializationUtils.class,
        ObjectMapper.class})
public class InfoDiodeSinkApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Метод тестирует контекст Spring.
     */
    public void contextLoads() {
        assertNotNull(this.applicationContext);
    }

    @Test
    public void testIncomingDataHandler() {
        // TODO realize
    }

}
