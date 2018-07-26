package concerttours.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotLoremIpsumValidator implements ConstraintValidator<NotLoremIpsum, String> {
    @Override
    public void initialize(final NotLoremIpsum notLoremIpsum) {

    }

    @Override
    public boolean isValid(final String s, final ConstraintValidatorContext constraintValidatorContext) {
        return s != null && !s.isEmpty() && !s.toLowerCase().startsWith("lorem ipsum");
    }
}
