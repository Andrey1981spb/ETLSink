package ru.voskhod.gpparf.integration.infodiode.sink.app;

import lombok.Data;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.voskhod.gpparf.integration.infodiode.sink.app.model.ContentAction;
import ru.voskhod.gpparf.integration.infodiode.sink.app.model.ContentModel;
import ru.voskhod.gpparf.integration.infodiode.sink.app.model.ServiceData;
import ru.voskhod.gpparf.integration.infodiode.sink.app.service.FileService;
import ru.voskhod.gpparf.integration.infodiode.sink.app.util.SerializationUtils;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Утильный сервис для работы с тестовыми данными.
 *
 * @author A.A.Dmitriev
 * @version %I%
 */
@Data
@Component
public class TestData {

    @Autowired
    private SerializationUtils serializationUtils;

    private final String uuidValue = UUID.randomUUID().toString();
    private static final String FIRST_ATTEMPT_POSTFIX = "1";
    private final UUID uuid = UUID.fromString(uuidValue);

    private final String contentActionValue = "SEND_NEW";
    private final ContentAction contentAction = ContentAction.valueOf(contentActionValue);

    private final String wrongContentActionValue = "WRONG_CONTENT_ACTION";
    private final ContentAction wrongContentAction = ContentAction.valueOf(wrongContentActionValue);

    private String payload;
    private static final String UID = "uid";
    public static final String ATTACHMENT_ID = "";
    private final String wrongTestMountPath = "0<>Oops.Its a wrong path!/";
    private static final String DATE_TIME_MESSAGE_SEND = "dateTimeMessageSend";
    private static final String GROUP_APP = "groupApp";
    private static final String PROCESS_OF_APP = "processOfApp";
    private static String current_local_date_time = "";

    @PostConstruct
    private void prepareTestEnvironment() {
        current_local_date_time = LocalDateTime.now().toString();
        payload = createTestJsonPayload();
    }

    /**
     * Метод возвращает тестовый json.
     *
     * @return String тестовый json
     */
    public String createTestIncomeJson() {
        JSONObject incomeJsonObject = new JSONObject();
        incomeJsonObject.put("uuid", uuidValue);
        incomeJsonObject.put("contentAction", contentActionValue);

        String payload = createTestJsonPayload();
        incomeJsonObject.put("payload", payload);
        return incomeJsonObject.toString();
    }

    private String createTestJsonPayload() {
        JSONObject payload = new JSONObject();
        payload.put(UID, ATTACHMENT_ID);
        return payload.toString();
    }

    /**
     * Метод возвращает dto с корректными тестовыми данными.
     *
     * @return dto с тестовыми данными
     */
    public ContentModel createContentDTO() {
        return ContentModel.builder()
                .uuid(uuid)
                .contentAction(contentAction)
                .payload(payload)
                .serviceData(createServiceData())
                .build();
    }

    private static ServiceData createServiceData() {
        return ServiceData.builder()
                .dateTimeMessageSend(LocalDateTime.parse(current_local_date_time))
                .build();
    }

    /**
     * Метод возвращает dto с тестовыми данными, содержащими неверный тип ContentAction.
     *
     * @return dto с тестовыми данными
     */
    public ContentModel createContentDTOWithWrongContentAction() {
        return ContentModel.builder()
                .uuid(uuid)
                .contentAction(wrongContentAction)
                .payload(payload)
                .build();
    }

    /**
     * Метод возвращает имя файла с постфиксом, отражающим
     * количество попыток отправки.
     *
     * @param contentModel дто контента
     * @return имя файла с постфиксом
     */
    public String createFileNameWithAttemptPostfix(final ContentModel contentModel) {
        return new StringBuilder().
                append(contentModel.getUuid().toString()).
                append(FileService.FILE_NAME_SEPARATOR).
                append(FIRST_ATTEMPT_POSTFIX).
                append(FileService.ZIP_FILE_EXTENSION).
                toString();
    }

}
