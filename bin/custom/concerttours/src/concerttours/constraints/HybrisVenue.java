package concerttours.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HybrisVenueValidator.class)
public @interface HybrisVenue {
    String message() default "{concerttours.constraints.NotLoremIpsum.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
