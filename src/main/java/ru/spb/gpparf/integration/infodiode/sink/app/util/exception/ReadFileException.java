package ru.spb.gpparf.integration.infodiode.sink.app.util.exception;

/**
 * Исключение чтения файлов.
 *
 * @author A.A.Dmitriev
 * @version %I%
 */
public class ReadFileException extends ProcessFileException {
    public ReadFileException(final String message, final Throwable err) {
        super(message, err);
    }

    public ReadFileException(final String message) {
        super(message);
    }
}
