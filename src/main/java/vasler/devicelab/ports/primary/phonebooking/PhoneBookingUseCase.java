package vasler.devicelab.ports.primary.phonebooking;

import vasler.devicelab.ports.primary.phonebooking.dto.*;

import java.util.List;

public interface PhoneBookingUseCase {
    public PhoneBookingResult bookPhone(PhoneBookingRequest phoneBookingRequest) throws Exception;
    public PhoneReturnResult returnPhone(PhoneReturnRequest phoneReturnRequest) throws Exception;
    public List<PhoneSummary> fetchPhonesBookedByTester(String tester) throws Exception;
    public List<PhoneTypeSummary> findAvailablePhoneTypes() throws Exception;
}
