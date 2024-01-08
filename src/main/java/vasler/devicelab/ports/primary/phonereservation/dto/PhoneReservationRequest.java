package vasler.devicelab.ports.primary.phonereservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import vasler.devicelab._util_.BeanValidator;
import vasler.devicelab.ports.primary.phonereservation.PhoneReservationUseCaseException;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PhoneReservationRequest {
    @NotNull
    private final String phoneType;
    @NotNull
    private final String tester;

    public static PhoneReservationRequestBuilder builder() {
        return new CustomPhoneReservationRequestBuilder();
    }

    private static class CustomPhoneReservationRequestBuilder extends PhoneReservationRequestBuilder {
        private boolean isValid(PhoneReservationRequest phoneReservationRequest) {
            return phoneReservationRequest.getPhoneType() != null;
        }

        public PhoneReservationRequest build() {
            PhoneReservationRequest phoneReservationRequest = super.build();
            var errors = BeanValidator.validate(phoneReservationRequest);
            if (!errors.isEmpty()) throw new PhoneReservationUseCaseException();

            return phoneReservationRequest;
        }
    }
}