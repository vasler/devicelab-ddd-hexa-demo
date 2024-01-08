package vasler.devicelab.domain.service;

import vasler.devicelab.ports.primary.phonereservation.dto.AvailablePhoneType;
import vasler.devicelab.ports.primary.phonereservation.dto.ReservedPhone;

import java.util.List;

public interface PhoneReservationDomainUseCase {
    public List<ReservedPhone> fetchPhonesReservedByTester(String tester) throws Exception;
    public List<AvailablePhoneType> findAvailablePhoneTypes() throws Exception;
}
