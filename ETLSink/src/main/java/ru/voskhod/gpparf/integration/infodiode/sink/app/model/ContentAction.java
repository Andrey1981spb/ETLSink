package ru.voskhod.gpparf.integration.infodiode.sink.app.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Типы сообщений.
 *
 * @author A.Dmitriev
 * @version %I%
 */

@NoArgsConstructor
@Getter
public enum ContentAction {

    SEND_NEW("SEND_NEW"),
    SEND_ACK("SEND_ACK"),
    WRONG_CONTENT_ACTION("WRONG_CONTENT_ACTION");

    private String levelCode;

    ContentAction(final String levelCode) {
        this.levelCode = levelCode;
    }

}

