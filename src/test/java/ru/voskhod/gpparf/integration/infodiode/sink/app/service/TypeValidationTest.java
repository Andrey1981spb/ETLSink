package ru.voskhod.gpparf.integration.infodiode.sink.app.service;

import org.junit.Assert;
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

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SinkApplicationTestConfiguration.class, TestData.class,
        TypeValidation.class, ApplicationConfig.class, ContentModel.class,
        SerializationUtils.class})
public class TypeValidationTest {

    @Autowired
    private ApplicationContext applicationContext;
    private ContentModel contentModel;
    @Autowired
    private TestData testData;
    @Autowired
    private Validator validator;

    /**
     * Метод тестирует контекст Spring.
     */
    public void contextLoads() {
        assertNotNull(this.applicationContext);
    }

    @Test
    public void testTypeValidation() {
        contentModel = testData.createContentDTOWithWrongContentAction();

        Set<ConstraintViolation<ContentModel>> validates = validator.validate(contentModel);
        Assert.assertTrue(validates.size() > 0);
    }

}
