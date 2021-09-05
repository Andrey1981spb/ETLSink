package ru.voskhod.gpparf.integration.infodiode.sink.app.util.validation;

import ru.voskhod.gpparf.integration.infodiode.sink.app.model.ContentAction;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидация ContentAction на соответствие типов сообщений.
 *
 * @author A.Dmitriev
 * @version %I%
 */
public class TypeValidation implements ConstraintValidator<TypeValid, ContentAction> {

    /**
     * Метод инициализирует аннотацию.
     *
     * @param constraintAnnotation - применяемая аннотация
     */
    @Override
    public void initialize(final TypeValid constraintAnnotation) {
    }

    /**
     * Метод проверяет соответствие типов сообщений допустимому набору значений.
     *
     * @param type    - проверяемый тип
     * @param context - контекст ConstraintValidator
     * @return булево значение, отражающее результат валидации
     */
    @Override
    public boolean isValid(final ContentAction type, final ConstraintValidatorContext context) {
        if (!(type.equals(ContentAction.SEND_NEW))
                && !(type.equals(ContentAction.SEND_ACK))) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Type " + type + " is not valid")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
