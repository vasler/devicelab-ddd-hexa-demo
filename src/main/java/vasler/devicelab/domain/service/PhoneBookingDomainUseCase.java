package vasler.devicelab.domain.service;

import vasler.devicelab.ports.primary.phonebooking.dto.PhoneTypeSummary;
import vasler.devicelab.ports.primary.phonebooking.dto.PhoneSummary;

import java.util.List;

public interface PhoneBookingDomainUseCase {
    public List<PhoneSummary> fetchPhonesBookedByTester(String tester) throws Exception;
    public List<PhoneTypeSummary> findAvailablePhoneTypes() throws Exception;
}
