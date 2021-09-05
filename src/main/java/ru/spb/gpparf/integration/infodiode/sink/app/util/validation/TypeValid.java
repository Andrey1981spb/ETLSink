package ru.spb.gpparf.integration.infodiode.sink.app.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = TypeValidation.class)
public @interface TypeValid {
    String message() default "{TypeValid.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
