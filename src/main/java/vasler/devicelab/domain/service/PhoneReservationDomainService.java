package vasler.devicelab.domain.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.types.Association;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vasler.devicelab.domain.model.phone.Phone;
import vasler.devicelab.domain.model.phonetype.PhoneType;
import vasler.devicelab.domain.model.tester.Tester;
import vasler.devicelab.ports.primary.phonereservation.dto.AvailablePhoneType;
import vasler.devicelab.ports.primary.phonereservation.dto.ReservedPhone;
import vasler.devicelab.ports.secondary.repository.PhoneTypes;
import vasler.devicelab.ports.secondary.repository.Phones;
import vasler.devicelab.ports.secondary.repository.Testers;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class PhoneReservationDomainService implements PhoneReservationDomainUseCase {
    private final Phones phones;
    private final PhoneTypes phoneTypes;
    private final Testers testers;

    // TOOD -- TAKEN A SHORTCUTS HERE
    // REFACTOR -- MOVE THE LOGIC TO THE AGREGGATES, USE STRATEGY PATTERN OR SIMILAR, REMOVE GETTERS

    @Override
    public List<ReservedPhone> fetchPhonesReservedByTester(String tester) throws Exception {
        List<Phone> reservedPhones = phones.findByReservedBy(Association.forId(new Tester.TesterId(tester)));

        return reservedPhones.stream()
            .map(p -> {
                PhoneType phoneType = phoneTypes.resolveRequired(p.getPhoneType());

                return ReservedPhone.builder()
                    .phoneType(phoneType.getName())
                    .phoneId(p.getId().id())
                    .reservedOn(p.getReservedOn())
                    .reservedBy(testers.resolveRequired(p.getReservedBy()).getId().id())
                    .build();
            })
            .collect(Collectors.toList());
    }

    @Override
    public List<AvailablePhoneType> findAvailablePhoneTypes() throws Exception {
        return phoneTypes.findAvailablePhoneTypes().stream()
            .map(pt -> AvailablePhoneType.builder().
                phoneTypeId(pt.getId().id())
                .phoneTypeName(pt.getName())
                .build()
            )
            .collect(Collectors.toList());
    }
}
