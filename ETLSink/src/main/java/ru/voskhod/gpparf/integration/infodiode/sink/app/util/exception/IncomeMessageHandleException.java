package ru.voskhod.gpparf.integration.infodiode.sink.app.util.exception;

/**
 * Исключение обработки входящих сообщений.
 *
 * @author A.A.Dmitriev
 * @version %I%
 */
public class IncomeMessageHandleException extends Exception {
    public IncomeMessageHandleException(final String message, final Throwable err) {
        super(message, err);
    }

    public IncomeMessageHandleException(final String message) {
        super(message);
    }
}
