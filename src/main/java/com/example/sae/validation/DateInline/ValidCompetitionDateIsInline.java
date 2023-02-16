package com.example.sae.validation.DateInline;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CompetitionDateInlineValidator.class)
@Documented
public @interface ValidCompetitionDateIsInline {
    String message() default "La date de d'inscription doit être antérieur a celle de début des matchs";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
