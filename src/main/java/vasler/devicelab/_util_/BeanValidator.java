package vasler.devicelab._util_;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.Set;

@Slf4j
public class BeanValidator {
    public static <T> Set<ConstraintViolation<T>> validate(T t) {
        var errors = SpringContext.getBean(Validator.class).validate(t);
        if (log.isDebugEnabled() && !errors.isEmpty()) {
            errors.forEach(e ->
                log.debug("-- Property '{}' has invalid value '{}' with validation message '{}'.",
                    e.getPropertyPath().toString(),
                    Optional.ofNullable(e.getInvalidValue()).orElse("NULL").toString(),
                    e.getMessage()
                )
            );
        }

        return errors;
    }
}
