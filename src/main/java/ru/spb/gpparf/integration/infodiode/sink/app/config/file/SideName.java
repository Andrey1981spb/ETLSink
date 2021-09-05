package ru.spb.gpparf.integration.infodiode.sink.app.config.file;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Обозначения сторон контура.
 *
 * @author A.Dmitriev
 * @version %I%
 */
@NoArgsConstructor
@Getter
public enum SideName {

    PUBLIC("public"),
    INTERNAL("internal");

    private String sideName;

    SideName(final String sideName) {
        this.sideName = sideName;
    }
}

