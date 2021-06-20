package concerttours.constraints;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HybrisVenueValidator implements ConstraintValidator<HybrisVenue, String> {
    @Override
    public void initialize(HybrisVenue hybrisVenue) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.hasText(s) && s.toLowerCase().contains("hybris");
    }
}
