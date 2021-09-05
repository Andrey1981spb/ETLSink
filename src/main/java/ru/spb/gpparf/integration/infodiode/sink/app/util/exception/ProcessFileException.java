package ru.spb.gpparf.integration.infodiode.sink.app.util.exception;

/**
 * Исключение записи-чтения файлов.
 * @author A.A.Dmitriev
 * @version %I%
 */
public class ProcessFileException extends IncomeMessageHandleException {
    public ProcessFileException(final String message, final Throwable err) {
        super(message, err);
    }
    public ProcessFileException(final String message) {
        super(message);
    }
}
