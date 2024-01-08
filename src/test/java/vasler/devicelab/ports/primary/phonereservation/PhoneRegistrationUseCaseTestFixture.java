package vasler.devicelab.ports.primary.phonereservation;

import org.jmolecules.ddd.types.Association;
import vasler.devicelab.domain.model.phone.Phone;
import vasler.devicelab.ports.primary.phonereservation.dto.PhoneReservationRequest;
import vasler.devicelab.ports.primary.phonereservation.dto.PhoneReturnRequest;
import vasler.devicelab.ports.secondary.repository.PhoneTypes;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PhoneRegistrationUseCaseTestFixture {
    public static PhoneReservationRequest buildValidPhoneRegistrationRequest() {
        return PhoneReservationRequest.builder().phoneType("Motorola_Nexus_6").tester("user.4").build();
    }

    public static PhoneReturnRequest buildPhoneReturnRequestWithUnknownTester() {
        return PhoneReturnRequest.builder().phoneId("35f29256-6717-4898-a734-26d055899603").tester("user.44").build();
    }

    public static PhoneReturnRequest buildPhoneReturnRequestWithInvalidPhoneId() {
        return PhoneReturnRequest.builder().phoneId("35f29256-6717-4898-a734-26d055899604").tester("user.4").build();
    }

    public static PhoneReturnRequest buildPhoneReturnRequestWithNonMatchingArguments() {
        return PhoneReturnRequest.builder().phoneId("35f29256-6717-4898-a734-26d055899603").tester("user.5").build();
    }

    public static PhoneReturnRequest buildValidPhoneReturnRequest() {
        return PhoneReturnRequest.builder().phoneId("35f29256-6717-4898-a734-26d055899603").tester("user.4").build();
    }

    public static PhoneReservationRequest buildPhoneRegistrationRequestForUnregisteredTester() {
        return PhoneReservationRequest.builder().phoneType("Motorola_Nexus_6").tester("user.unregistered").build();
    }

    public static PhoneReservationRequest buildPhoneRegistrationRequestForUnknownPhoneType() {
        return PhoneReservationRequest.builder().phoneType("Unknown_Phone_Type").tester("user.4").build();
    }

    public static List<Phone> buildAllPhones(PhoneTypes phoneTypes, Set<String> excludeList) {
        return StreamSupport.stream(phoneTypes.findAll().spliterator(), false)
            .filter(phoneType -> !excludeList.contains(phoneType.getId().id()))
            .map(phoneType -> {
                return Phone.builder()
                    .phoneType(Association.forAggregate(phoneType))
                    .id(new Phone.PhoneId(UUID.randomUUID()))
                    .available(true)
                    .build();
            })
            .collect(Collectors.toList());
    }
}
