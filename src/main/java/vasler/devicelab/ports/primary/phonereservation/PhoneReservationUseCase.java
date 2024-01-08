package vasler.devicelab.ports.primary.phonereservation;

import vasler.devicelab.ports.primary.phonereservation.dto.*;

import java.util.List;

public interface PhoneReservationUseCase {
    public PhoneReservationResult reservePhone(PhoneReservationRequest phoneReservationRequest) throws Exception;
    public PhoneReturnResult returnPhone(PhoneReturnRequest phoneReturnRequest) throws Exception;
    public List<ReservedPhone> fetchPhonesReservedByTester(String tester) throws Exception;
    public List<AvailablePhoneType> findAvailablePhoneTypes() throws Exception;
}
