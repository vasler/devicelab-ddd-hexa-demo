package vasler.devicelab.ports.primary.phonereservation.dto;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
public class AvailablePhoneType {
    private final String phoneTypeId;
    private final String phoneTypeName;
}
