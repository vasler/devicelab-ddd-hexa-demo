package vasler.devicelab.domain.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.types.Association;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vasler.devicelab.domain.model.phone.Phone;
import vasler.devicelab.domain.model.phonetype.PhoneType;
import vasler.devicelab.domain.model.tester.Tester;
import vasler.devicelab.ports.primary.phonebooking.dto.PhoneTypeSummary;
import vasler.devicelab.ports.primary.phonebooking.dto.PhoneSummary;
import vasler.devicelab.ports.secondary.repository.PhoneTypes;
import vasler.devicelab.ports.secondary.repository.Phones;
import vasler.devicelab.ports.secondary.repository.Testers;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class PhoneBookingDomainService implements PhoneBookingDomainUseCase {
    private final Phones phones;
    private final PhoneTypes phoneTypes;
    private final Testers testers;

    // TODO -- TAKEN A SHORTCUTS HERE
    // REFACTOR --  MOVE THE LOGIC INTO AGREGGATES (USE STRATEGY PATTERN OR SIMILAR / REMOVE GETTERS)
    //              OR USE ARCHUNIT AND ALLOW THE GETTERS TO BE CALLED ONLY FROM DOMAIN SERVICES

    @Override
    public List<PhoneSummary> fetchPhonesBookedByTester(String tester) throws Exception {
        List<Phone> bookedPhones = phones.findByBookedBy(Association.forId(new Tester.TesterId(tester)));

        return bookedPhones.stream()
            .map(p -> {
                PhoneType phoneType = phoneTypes.resolveRequired(p.getPhoneType());

                return PhoneSummary.builder()
                    .phoneType(phoneType.getName())
                    .phoneId(p.getId().id())
                    .bookedOn(p.getBookedOn())
                    .bookedBy(testers.resolveRequired(p.getBookedBy()).getId().id())
                    .build();
            })
            .collect(Collectors.toList());
    }

    @Override
    public List<PhoneTypeSummary> findAvailablePhoneTypes() throws Exception {
        return phoneTypes.findAvailablePhoneTypes().stream()
            .map(pt -> PhoneTypeSummary.builder().
                phoneTypeId(pt.getId().id())
                .phoneTypeName(pt.getName())
                .build()
            )
            .collect(Collectors.toList());
    }
}
