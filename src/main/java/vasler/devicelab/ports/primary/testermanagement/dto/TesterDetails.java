package vasler.devicelab.ports.primary.testermanagement.dto;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
public class TesterDetails {
    private final String testerId;
}
