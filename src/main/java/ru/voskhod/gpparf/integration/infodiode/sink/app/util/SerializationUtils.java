package ru.voskhod.gpparf.integration.infodiode.sink.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.voskhod.gpparf.integration.infodiode.sink.app.util.exception.ReadFileException;
import ru.voskhod.gpparf.integration.infodiode.sink.app.util.exception.ProcessFileException;

/**
 * Утилитный сервис предоставляющий инструменты сериализации и десериализации.
 *
 * @author A.Dmitriev
 * @version %I%
 */
@Component
public final class SerializationUtils {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Метод преобразует строку в объект.
     *
     * @param <T> тип объекта
     * @param onjJSONString строка для преобразования
     * @param clazz класс для которого создается объект из строки
     * @return T объект класса
     *
     * @throws ReadFileException исключение преобразования строки в объект
     */
    public <T> T jSONStringToObject(final String onjJSONString, final Class<T> clazz)
            throws ReadFileException {
        try {
            return objectMapper.readValue(onjJSONString, clazz);
        } catch (JsonProcessingException exception) {
            throw new ReadFileException("Ошибка преобразования строки в объект по причине : ",
                    exception);
        }
    }

    /**
     * Метод преобразует объект в строку.
     *
     * @param <T> тип объекта
     * @param obj объект для сериализации
     * @throws ProcessFileException исключение преобразования объекта в строку
     * @return строка
     *
     */
    public <T> String jSONObjectToString(final T obj) throws ProcessFileException {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException exception) {
            throw new ProcessFileException("Ошибка преобразования объекта в строку по причине : ",
                    exception);
        }
    }

}
