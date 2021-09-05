package ru.voskhod.gpparf.integration.infodiode.sink.app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.voskhod.gpparf.integration.infodiode.sink.app.model.ContentModel;
import ru.voskhod.gpparf.integration.infodiode.sink.app.model.ServiceData;

import java.time.LocalDateTime;

/**
 * Сервис работы с ContentDTO.
 *
 * @author A.Dmitriev
 * @version %I%
 */
@Service
@Slf4j
public class ContentService {

    public static final Integer FIRST_DISPATCH = 1;

    /**
     * Обновляет в отправляемом сообщении поле даты-времени отправки сообщания
     * в другой контур.
     *
     * @param contentModel модель данных
     *                     в другой контур
     */
    public void updateDateTimeMessageSend(final ContentModel contentModel) {
        if (isFirstAttempt(contentModel)) {
            ServiceData serviceData = contentModel.getServiceData();
            LocalDateTime currentDateTime = LocalDateTime.now();
            serviceData.setDateTimeMessageSend(currentDateTime);
        }
    }

    private boolean isFirstAttempt(final ContentModel contentModel) {
        int numberAttempt = contentModel.getNumberOfDispatches();
        return numberAttempt == FIRST_DISPATCH;
    }

}
