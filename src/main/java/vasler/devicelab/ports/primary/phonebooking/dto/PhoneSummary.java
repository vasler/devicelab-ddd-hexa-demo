package vasler.devicelab.ports.primary.phonebooking.dto;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
@ToString
public class PhoneSummary {
    private final UUID phoneId;
    private final String phoneType;
    private final String bookedBy;
    private final LocalDateTime bookedOn;
}
