package vasler.devicelab.ports.primary.phonereservation;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import vasler.devicelab._config_.PostgresTestConfiguration;
import vasler.devicelab.domain.model.phone.Phone;
import vasler.devicelab.ports.primary.phonereservation.dto.*;
import vasler.devicelab.ports.secondary.repository.PhoneTypes;
import vasler.devicelab.ports.secondary.repository.Phones;

import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(PostgresTestConfiguration.class)
class PhoneReservationAndReturnUseCaseTest {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(PhoneReservationAndReturnUseCaseTest.class);

	@SpyBean
	private Phones phones;

	@SpyBean
	private PhoneTypes phoneTypes;

	@SpyBean
	private PhoneReservationUseCase phoneReservationUseCase;

	@Test
	@Order(1)
	@Commit
	void givenPhoneAvailableForPhoneType_whenReservingPhone_PhoneSuccessfullyReserved() throws Exception {
		PhoneReservationRequest phoneReservationRequest = PhoneRegistrationUseCaseTestFixture.buildValidPhoneRegistrationRequest();

		PhoneReservationResult phoneReservationResult = phoneReservationUseCase.reservePhone(phoneReservationRequest);
		UUID reservedPhoneId = phoneReservationResult.getPhoneId();
		Assertions.assertTrue(phoneReservationResult.isSuccessful());
		Assertions.assertEquals(PhoneReservationResult.Message.SUCCESS, phoneReservationResult.getMessage());

		Phone reservedPhone = phones.findById(new Phone.PhoneId(phoneReservationResult.getPhoneId())).get();
		Assertions.assertFalse(reservedPhone.isAvailable());
	}

	@Test
	@Order(2)
	@Commit
	void givenPhoneNotAvailableForPhoneType_whenReservingPhone_PhoneNotReserved() throws Exception {
		PhoneReservationRequest phoneReservationRequest = PhoneRegistrationUseCaseTestFixture.buildValidPhoneRegistrationRequest();

		PhoneReservationResult phoneReservationResult = phoneReservationUseCase.reservePhone(phoneReservationRequest);
		Assertions.assertFalse(phoneReservationResult.isSuccessful());
		Assertions.assertEquals(PhoneReservationResult.Message.PHONE_NOT_AVAILABLE, phoneReservationResult.getMessage());
	}

	@Test
	@Order(3)
	@Commit
	void givenTesterNotRegistered_whenReservingPhone_PhoneNotReserved() throws Exception {
		PhoneReservationRequest phoneReservationRequest = PhoneRegistrationUseCaseTestFixture.buildPhoneRegistrationRequestForUnregisteredTester();

		PhoneReservationResult phoneReservationResult = phoneReservationUseCase.reservePhone(phoneReservationRequest);
		Assertions.assertFalse(phoneReservationResult.isSuccessful());
		Assertions.assertEquals(PhoneReservationResult.Message.TESTER_NOT_REGISTERED, phoneReservationResult.getMessage());
	}

	@Test
	@Order(4)
	@Commit
	void givenPhoneTypeUnknown_whenReservingPhone_PhoneNotReserved() throws Exception {
		PhoneReservationRequest phoneReservationRequest = PhoneRegistrationUseCaseTestFixture.buildPhoneRegistrationRequestForUnknownPhoneType();

		PhoneReservationResult phoneReservationResult = phoneReservationUseCase.reservePhone(phoneReservationRequest);
		Assertions.assertFalse(phoneReservationResult.isSuccessful());
		Assertions.assertEquals(PhoneReservationResult.Message.PHONE_TYPE_UNKNOWN, phoneReservationResult.getMessage());
	}

	@Test
	@Order(5)
	@Transactional
	@Commit
	void whenListingAvailablePhoneTypes_ReservedPhoneIsMissingFromTheList() throws Exception {
		List<AvailablePhoneType> availablePhoneTypes = phoneReservationUseCase.findAvailablePhoneTypes();

		for (AvailablePhoneType phoneType : availablePhoneTypes) {
			log.debug("Available phone type: {}", phoneType.getPhoneTypeName());
		}

		Assertions.assertEquals(8, availablePhoneTypes.size());
		Assertions.assertTrue(availablePhoneTypes.stream()
			.filter(pt -> pt.equals("35f29256-6717-4898-a734-26d055899603"))
			.findAny().isEmpty()
		);
	}

	@Test
	@Order(6)
	@Commit
	void whenListingPhonesReservedByTester_CorrectListIsReturned() throws Exception {
		List<ReservedPhone> reservedPhones = phoneReservationUseCase.fetchPhonesReservedByTester("user.4");

		for (ReservedPhone reservedPhone : reservedPhones) {
			log.info("Available phone type: {}", reservedPhone);
		}
		Assertions.assertEquals(1, reservedPhones.size());
		Assertions.assertEquals("35f29256-6717-4898-a734-26d055899603", reservedPhones.get(0).getPhoneId().toString());
	}

	@Test
	@Order(7)
	@Commit
	void givenTesterUnknown_whenReturningPhone_PhoneNotReturned() throws Exception {
		PhoneReturnRequest phoneReturnRequest = PhoneRegistrationUseCaseTestFixture.buildPhoneReturnRequestWithUnknownTester();

		PhoneReturnResult phoneReturnResult = phoneReservationUseCase.returnPhone(phoneReturnRequest);
		Assertions.assertFalse(phoneReturnResult.isSuccessful());
		Assertions.assertEquals(PhoneReturnResult.Message.TESTER_NOT_REGISTERED, phoneReturnResult.getMessage());
	}

	@Test
	@Order(8)
	@Commit
	void givenTesterAndPhoneIdDontMatch_whenReturningPhone_PhoneNotReturned() throws Exception {
		PhoneReturnRequest phoneReturnRequest = PhoneRegistrationUseCaseTestFixture.buildPhoneReturnRequestWithNonMatchingArguments();

		PhoneReturnResult phoneReturnResult = phoneReservationUseCase.returnPhone(phoneReturnRequest);
		Assertions.assertFalse(phoneReturnResult.isSuccessful());
		Assertions.assertEquals(PhoneReturnResult.Message.PHONE_NOT_FOUND, phoneReturnResult.getMessage());
	}

	@Test
	@Order(9)
	@Commit
	void givenPhoneIdArgumentInvalid_whenReturningPhone_PhoneNotReturned() throws Exception {
		PhoneReturnRequest phoneReturnRequest = PhoneRegistrationUseCaseTestFixture.buildPhoneReturnRequestWithInvalidPhoneId();

		PhoneReturnResult phoneReturnResult = phoneReservationUseCase.returnPhone(phoneReturnRequest);
		Assertions.assertFalse(phoneReturnResult.isSuccessful());
		Assertions.assertEquals(PhoneReturnResult.Message.PHONE_NOT_FOUND, phoneReturnResult.getMessage());
	}

	@Test
	@Order(10)
	@Commit
	void givenPhoneReturnRequestValid_whenReturningPhone_PhoneReturned() throws Exception {
		PhoneReturnRequest phoneReturnRequest = PhoneRegistrationUseCaseTestFixture.buildValidPhoneReturnRequest();

		PhoneReturnResult phoneReturnResult = phoneReservationUseCase.returnPhone(phoneReturnRequest);
		Assertions.assertTrue(phoneReturnResult.isSuccessful());
		Assertions.assertEquals(PhoneReturnResult.Message.SUCCESS, phoneReturnResult.getMessage());

		Phone returnedPhone = phones.findById(new Phone.PhoneId(phoneReturnResult.getPhoneId())).get();
		Assertions.assertTrue(returnedPhone.isAvailable());
	}
}
