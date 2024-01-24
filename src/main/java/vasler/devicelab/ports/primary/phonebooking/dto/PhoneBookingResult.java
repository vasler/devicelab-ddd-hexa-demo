package vasler.devicelab.ports.primary.phonebooking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.UUID;

@Value
@Getter
@Builder
public class PhoneBookingResult {
    public static enum Message {
        PHONE_TYPE_UNKNOWN,
        TESTER_NOT_REGISTERED,
        PHONE_NOT_AVAILABLE,
        SUCCESS
    }

    private final boolean successful;
    private final UUID phoneId;
    private final Message message;
}
