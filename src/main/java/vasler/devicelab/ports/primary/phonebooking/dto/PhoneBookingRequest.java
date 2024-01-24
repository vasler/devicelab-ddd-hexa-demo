package vasler.devicelab.ports.primary.phonebooking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import vasler.devicelab._util_.BeanValidator;
import vasler.devicelab.ports.primary.phonebooking.PhoneBookingUseCaseException;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PhoneBookingRequest {
    @NotNull
    private final String phoneType;
    @NotNull
    private final String tester;

    public static PhoneBookingRequestBuilder builder() {
        return new CustomPhoneBookingRequestBuilder();
    }

    private static class CustomPhoneBookingRequestBuilder extends PhoneBookingRequestBuilder {
        private boolean isValid(PhoneBookingRequest phoneBookingRequest) {
            return phoneBookingRequest.getPhoneType() != null;
        }

        public PhoneBookingRequest build() {
            PhoneBookingRequest phoneBookingRequest = super.build();
            var errors = BeanValidator.validate(phoneBookingRequest);
            if (!errors.isEmpty()) throw new PhoneBookingUseCaseException();

            return phoneBookingRequest;
        }
    }
}