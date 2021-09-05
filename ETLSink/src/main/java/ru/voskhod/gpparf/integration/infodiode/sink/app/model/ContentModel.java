package ru.voskhod.gpparf.integration.infodiode.sink.app.model;

import lombok.*;
import ru.voskhod.gpparf.integration.infodiode.sink.app.util.validation.TypeValid;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * Модель данных из входящего сообщения.
 *
 * @author A.Dmitriev
 * @version %I%
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ContentModel implements Serializable {
    @NotNull
    private UUID uuid;
    @TypeValid
    @NotNull
    private ContentAction contentAction;
    private String payload;
    private Integer numberOfDispatches;
    private ServiceData serviceData;
}
