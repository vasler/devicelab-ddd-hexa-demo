package vasler.devicelab.ports.primary.phonereservation.dto;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
@ToString
public class ReservedPhone {
    private final UUID phoneId;
    private final String phoneType;
    private final String reservedBy;
    private final LocalDateTime reservedOn;
}
