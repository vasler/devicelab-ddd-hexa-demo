package vasler.devicelab.service.phonereservation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.types.Association;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vasler.devicelab.domain.model.phone.Phone;
import vasler.devicelab.domain.model.phonetype.PhoneType;
import vasler.devicelab.domain.model.tester.Tester;
import vasler.devicelab.domain.service.PhoneReservationDomainUseCase;
import vasler.devicelab.ports.primary.phonereservation.dto.*;
import vasler.devicelab.ports.primary.phonereservation.PhoneReservationUseCase;
import vasler.devicelab.ports.secondary.repository.PhoneTypes;
import vasler.devicelab.ports.secondary.repository.Phones;
import vasler.devicelab.ports.secondary.repository.Testers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class PhoneReservationService implements PhoneReservationUseCase {
    private final Phones phones;
    private final PhoneTypes phoneTypes;
    private final Testers testers;
    private final PhoneReservationDomainUseCase phoneReservationDomainUseCase;

    @Override
    public PhoneReservationResult reservePhone(PhoneReservationRequest phoneReservationRequest) throws Exception {
        Optional<PhoneType> phoneType = phoneTypes.findById(new PhoneType.PhoneTypeId(phoneReservationRequest.getPhoneType()));
        if (phoneType.isEmpty()) return buildFailedReservationResult(PhoneReservationResult.Message.PHONE_TYPE_UNKNOWN);

        Optional<Tester> tester = testers.findById(new Tester.TesterId(phoneReservationRequest.getTester()));
        if (tester.isEmpty()) return buildFailedReservationResult(PhoneReservationResult.Message.TESTER_NOT_REGISTERED);

        List<Phone> availablePhones = phones.findAvailablePhonesByPhoneType(Association.forAggregate(phoneType.get()));
        if (availablePhones.isEmpty()) return buildFailedReservationResult(PhoneReservationResult.Message.PHONE_NOT_AVAILABLE);

        Phone phoneToReserve = availablePhones.get(0);
        if (!phoneToReserve.reservePhone(tester.get()))
            return buildFailedReservationResult(PhoneReservationResult.Message.PHONE_NOT_AVAILABLE);

        return PhoneReservationResult.builder()
            .successful(true)
            .phoneId(phoneToReserve.getId().id())
            .message(PhoneReservationResult.Message.SUCCESS)
            .build();
    }

    @Override
    public PhoneReturnResult returnPhone(PhoneReturnRequest phoneReturnRequest) throws Exception {
        Optional<Phone> phone = phones.findById(new Phone.PhoneId(UUID.fromString(phoneReturnRequest.getPhoneId())));
        if (phone.isEmpty()) return buildFailedReturnResult(PhoneReturnResult.Message.PHONE_NOT_FOUND);

        Optional<Tester> tester = testers.findById(new Tester.TesterId(phoneReturnRequest.getTester()));
        if (tester.isEmpty()) return buildFailedReturnResult(PhoneReturnResult.Message.TESTER_NOT_REGISTERED);

        if (!phone.get().returnPhone(tester.get()))
            return buildFailedReturnResult(PhoneReturnResult.Message.PHONE_NOT_FOUND);

        return PhoneReturnResult.builder()
            .successful(true)
            .phoneId(phone.get().getId().id())
            .message(PhoneReturnResult.Message.SUCCESS)
            .build();
    }

    @Override
    public List<ReservedPhone> fetchPhonesReservedByTester(String tester) throws Exception {
        return phoneReservationDomainUseCase.fetchPhonesReservedByTester(tester);
    }

    @Override
    public List<AvailablePhoneType> findAvailablePhoneTypes() throws Exception {
        return phoneReservationDomainUseCase.findAvailablePhoneTypes();
    }

    private static PhoneReservationResult buildFailedReservationResult(PhoneReservationResult.Message message) {
        return PhoneReservationResult.builder().successful(false).message(message).build();
    }

    private static PhoneReturnResult buildFailedReturnResult(PhoneReturnResult.Message message) {
        return PhoneReturnResult.builder().successful(false).message(message).build();
    }
}
