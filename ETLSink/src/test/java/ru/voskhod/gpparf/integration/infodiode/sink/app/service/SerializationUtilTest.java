package ru.voskhod.gpparf.integration.infodiode.sink.app.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.voskhod.gpparf.integration.infodiode.sink.app.TestData;
import ru.voskhod.gpparf.integration.infodiode.sink.app.config.ApplicationConfig;
import ru.voskhod.gpparf.integration.infodiode.sink.app.model.ContentModel;
import ru.voskhod.gpparf.integration.infodiode.sink.app.util.SerializationUtils;
import ru.voskhod.gpparf.integration.infodiode.sink.app.util.validation.TypeValidation;
import ru.voskhod.gpparf.integration.infodiode.sink.config.SinkApplicationTestConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SinkApplicationTestConfiguration.class,
        TestData.class, TypeValidation.class, ApplicationConfig.class, ContentModel.class,
        SerializationUtils.class, ContentService.class})
public class SerializationUtilTest {

    private static final String SERVICE_DATA = "serviceData";

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private TestData testData;
    @Autowired
    private SerializationUtils serializationUtils;

    /**
     * Метод тестирует контекст Spring.
     */
    public void contextLoads() {
        assertNotNull(this.applicationContext);
    }

    @Test
    public void testTransferJSONToDTO()
            throws Exception {
        String incomeJson = testData.createTestIncomeJson();
        ContentModel expectedContentModel = testData.createContentDTO();

        ContentModel returnedContentModel = serializationUtils.jSONStringToObject(incomeJson, ContentModel.class);
        assertThat(expectedContentModel).isEqualToIgnoringGivenFields(returnedContentModel, SERVICE_DATA);
    }

}
