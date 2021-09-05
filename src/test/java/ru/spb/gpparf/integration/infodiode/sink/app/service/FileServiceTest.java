package ru.spb.gpparf.integration.infodiode.sink.app.service;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.spb.gpparf.integration.infodiode.sink.app.TestData;
import ru.spb.gpparf.integration.infodiode.sink.app.config.ApplicationConfig;
import ru.spb.gpparf.integration.infodiode.sink.app.config.file.AppFileSupplierImpl;
import ru.spb.gpparf.integration.infodiode.sink.app.model.ContentModel;
import ru.spb.gpparf.integration.infodiode.sink.app.service.impl.LocalStorageAttachmentReadServiceImpl;
import ru.spb.gpparf.integration.infodiode.sink.app.util.SerializationUtils;
import ru.spb.gpparf.integration.infodiode.sink.app.config.file.FileSettings;
import ru.voskhod.gpparf.integration.infodiode.sink.config.SinkApplicationTestConfiguration;
import ru.voskhod.gpparf.integration.infodiode.sink.config.TestFileSupplierImpl;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@EnableConfigurationProperties({FileSettings.class})
@SpringBootTest(classes = {FileService.class, SinkApplicationTestConfiguration.class, TestData.class,
        TestUtil.class, ApplicationConfig.class, ContentModel.class, TestFileSupplierImpl.class,
        AppFileSupplierImpl.class, SerializationUtils.class, AttachmentWriteService.class, AttachmentReadService.class,
        LocalStorageAttachmentReadServiceImpl.class})
public class FileServiceTest {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private FileService fileService;
    private ContentModel contentModel;
    @Autowired
    private TestData testData;
    @Autowired
    private FileSystem fileSystem;
    @Autowired
    private FileSettings configurator;
    @Autowired
    private TestUtil testUtil;

    //TODO реализовать при решении проблем с фэйковой файловой системой

    //  @Before
    //  public void prepareAttachment() throws WriteFileException, ReadFileException {
    //       testUtil.writeTestFileAttachment();
    //  }

    @After
    public void clearTestDirectory() {
        testUtil.clearTestDirectory();
    }

    /**
     * Метод тестирует контекст Spring.
     */
    public void contextLoads() {
        assertNotNull(this.applicationContext);
    }

    @Test
    public void testTransferToFileOutput() throws Exception {
        contentModel = testData.createContentDTO();
        Path pathToStore = fileSystem.getPath(configurator.createOutgoingDirectoryToFileAsString());
        fileService.writeFile(contentModel);
        String expectedFileName = testData.createFileNameWithAttemptPostfix(contentModel);
        Path pathFile = pathToStore.resolve(expectedFileName);
        assertTrue(Files.exists(pathFile));
    }

}
