package vasler.devicelab.service.phonebooking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.types.Association;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vasler.devicelab.domain.model.phone.Phone;
import vasler.devicelab.domain.model.phonetype.PhoneType;
import vasler.devicelab.domain.model.tester.Tester;
import vasler.devicelab.domain.service.PhoneBookingDomainUseCase;
import vasler.devicelab.ports.primary.phonebooking.dto.*;
import vasler.devicelab.ports.primary.phonebooking.PhoneBookingUseCase;
import vasler.devicelab.ports.secondary.repository.PhoneTypes;
import vasler.devicelab.ports.secondary.repository.Phones;
import vasler.devicelab.ports.secondary.repository.Testers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class PhoneBookingService implements PhoneBookingUseCase {
    private final Phones phones;
    private final PhoneTypes phoneTypes;
    private final Testers testers;
    private final PhoneBookingDomainUseCase phoneBookingDomainUseCase;

    @Override
    public PhoneBookingResult bookPhone(PhoneBookingRequest phoneBookingRequest) throws Exception {
        Optional<PhoneType> phoneType = phoneTypes.findById(new PhoneType.PhoneTypeId(phoneBookingRequest.getPhoneType()));
        if (phoneType.isEmpty()) return buildFailedBookingResult(PhoneBookingResult.Message.PHONE_TYPE_UNKNOWN);

        Optional<Tester> tester = testers.findById(new Tester.TesterId(phoneBookingRequest.getTester()));
        if (tester.isEmpty()) return buildFailedBookingResult(PhoneBookingResult.Message.TESTER_NOT_REGISTERED);

        List<Phone> availablePhones = phones.findAvailablePhonesByPhoneType(Association.forAggregate(phoneType.get()));
        if (availablePhones.isEmpty()) return buildFailedBookingResult(PhoneBookingResult.Message.PHONE_NOT_AVAILABLE);

        Phone phoneToBook = availablePhones.get(0);
        if (!phoneToBook.bookPhone(tester.get()))
            return buildFailedBookingResult(PhoneBookingResult.Message.PHONE_NOT_AVAILABLE);

        return PhoneBookingResult.builder()
            .successful(true)
            .phoneId(phoneToBook.getId().id())
            .message(PhoneBookingResult.Message.SUCCESS)
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
    public List<PhoneSummary> fetchPhonesBookedByTester(String tester) throws Exception {
        return phoneBookingDomainUseCase.fetchPhonesBookedByTester(tester);
    }

    @Override
    public List<PhoneTypeSummary> findAvailablePhoneTypes() throws Exception {
        return phoneBookingDomainUseCase.findAvailablePhoneTypes();
    }

    private static PhoneBookingResult buildFailedBookingResult(PhoneBookingResult.Message message) {
        return PhoneBookingResult.builder().successful(false).message(message).build();
    }

    private static PhoneReturnResult buildFailedReturnResult(PhoneReturnResult.Message message) {
        return PhoneReturnResult.builder().successful(false).message(message).build();
    }
}
