package com.example.sae.validation.DateInline;

import com.example.sae.models.db.Competition;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CompetitionDateInlineValidator implements ConstraintValidator<ValidCompetitionDateIsInline, Competition> {
    @Override
    public boolean isValid(Competition competition, ConstraintValidatorContext constraintValidatorContext) {
        return competition.getDateFinInscription().isBefore(competition.getDateDebutCompetition());
    }
}
