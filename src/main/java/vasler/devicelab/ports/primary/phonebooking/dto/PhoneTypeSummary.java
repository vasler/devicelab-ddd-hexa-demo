package vasler.devicelab.ports.primary.phonebooking.dto;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
public class PhoneTypeSummary {
    private final String phoneTypeId;
    private final String phoneTypeName;
}
