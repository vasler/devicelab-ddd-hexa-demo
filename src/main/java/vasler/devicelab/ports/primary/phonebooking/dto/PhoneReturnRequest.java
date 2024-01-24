package vasler.devicelab.ports.primary.phonebooking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import vasler.devicelab._util_.BeanValidator;
import vasler.devicelab.ports.primary.phonebooking.PhoneBookingUseCaseException;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PhoneReturnRequest {
    @NotNull
    private final String phoneId;
    @NotNull
    private final String tester;

    public static PhoneReturnRequest.PhoneReturnRequestBuilder builder() {
        return new CustomPhoneReturnRequestBuilder();
    }

    private static class CustomPhoneReturnRequestBuilder extends PhoneReturnRequest.PhoneReturnRequestBuilder {
        public PhoneReturnRequest build() {
            PhoneReturnRequest phoneReturnRequest = super.build();
            var errors = BeanValidator.validate(phoneReturnRequest);
            if (!errors.isEmpty()) throw new PhoneBookingUseCaseException();

            return phoneReturnRequest;
        }
    }
}