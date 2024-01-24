package vasler.devicelab.ports.primary.phonebooking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.UUID;

@Value
@Getter
@Builder
public class PhoneReturnResult {
    public static enum Message {
        TESTER_NOT_REGISTERED,
        PHONE_NOT_FOUND,
        SUCCESS
    }

    private final boolean successful;
    private final UUID phoneId;
    private final Message message;
}
