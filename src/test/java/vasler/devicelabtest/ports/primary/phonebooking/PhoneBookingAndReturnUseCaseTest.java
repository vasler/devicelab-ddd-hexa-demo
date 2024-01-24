package vasler.devicelabtest.ports.primary.phonebooking;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import vasler.devicelabtest._config_.PostgresTestConfiguration;
import vasler.devicelab.domain.model.phone.Phone;
import vasler.devicelab.ports.primary.phonebooking.PhoneBookingUseCase;
import vasler.devicelab.ports.primary.phonebooking.dto.*;
import vasler.devicelab.ports.secondary.repository.PhoneTypes;
import vasler.devicelab.ports.secondary.repository.Phones;
import vasler.devicelabtest._config_.TestConfig;

import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import({PostgresTestConfiguration.class})
class PhoneBookingAndReturnUseCaseTest {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(PhoneBookingAndReturnUseCaseTest.class);

	@SpyBean
	private Phones phones;

	@SpyBean
	private PhoneTypes phoneTypes;

	@SpyBean
	private PhoneBookingUseCase phoneBookingUseCase;

	@Test
	@Order(1)
	@Commit
	void givenPhoneAvailableForPhoneType_whenBookingPhone_PhoneSuccessfullyBooked() throws Exception {
		PhoneBookingRequest phoneBookingRequest = PhoneBookingUseCaseTestFixture.buildValidPhoneRegistrationRequest();

		PhoneBookingResult phoneBookingResult = phoneBookingUseCase.bookPhone(phoneBookingRequest);
		UUID bookedPhoneId = phoneBookingResult.getPhoneId();
		Assertions.assertTrue(phoneBookingResult.isSuccessful());
		Assertions.assertEquals(PhoneBookingResult.Message.SUCCESS, phoneBookingResult.getMessage());

		Phone bookedPhone = phones.findById(new Phone.PhoneId(phoneBookingResult.getPhoneId())).get();
		Assertions.assertFalse(bookedPhone.isAvailable());
	}

	@Test
	@Order(2)
	@Commit
	void givenPhoneNotAvailableForPhoneType_whenBookingPhone_PhoneNotBooked() throws Exception {
		PhoneBookingRequest phoneBookingRequest = PhoneBookingUseCaseTestFixture.buildValidPhoneRegistrationRequest();

		PhoneBookingResult phoneBookingResult = phoneBookingUseCase.bookPhone(phoneBookingRequest);
		Assertions.assertFalse(phoneBookingResult.isSuccessful());
		Assertions.assertEquals(PhoneBookingResult.Message.PHONE_NOT_AVAILABLE, phoneBookingResult.getMessage());
	}

	@Test
	@Order(3)
	@Commit
	void givenTesterNotRegistered_whenBookingPhone_PhoneNotBooked() throws Exception {
		PhoneBookingRequest phoneBookingRequest = PhoneBookingUseCaseTestFixture.buildPhoneRegistrationRequestForUnregisteredTester();

		PhoneBookingResult phoneBookingResult = phoneBookingUseCase.bookPhone(phoneBookingRequest);
		Assertions.assertFalse(phoneBookingResult.isSuccessful());
		Assertions.assertEquals(PhoneBookingResult.Message.TESTER_NOT_REGISTERED, phoneBookingResult.getMessage());
	}

	@Test
	@Order(4)
	@Commit
	void givenPhoneTypeUnknown_whenBookingPhone_PhoneNotBooked() throws Exception {
		PhoneBookingRequest phoneBookingRequest = PhoneBookingUseCaseTestFixture.buildPhoneRegistrationRequestForUnknownPhoneType();

		PhoneBookingResult phoneBookingResult = phoneBookingUseCase.bookPhone(phoneBookingRequest);
		Assertions.assertFalse(phoneBookingResult.isSuccessful());
		Assertions.assertEquals(PhoneBookingResult.Message.PHONE_TYPE_UNKNOWN, phoneBookingResult.getMessage());
	}

	@Test
	@Order(5)
	@Transactional
	@Commit
	void whenListingAvailablePhoneTypes_BookedPhoneIsMissingFromTheList() throws Exception {
		List<PhoneTypeSummary> phoneTypeSummaries = phoneBookingUseCase.findAvailablePhoneTypes();

		for (PhoneTypeSummary phoneType : phoneTypeSummaries) {
			log.debug("Available phone type: {}", phoneType.getPhoneTypeName());
		}

		Assertions.assertEquals(8, phoneTypeSummaries.size());
		Assertions.assertTrue(phoneTypeSummaries.stream()
			.filter(pt -> pt.equals("35f29256-6717-4898-a734-26d055899603"))
			.findAny().isEmpty()
		);
	}

	@Test
	@Order(6)
	@Commit
	void whenListingPhonesBookedByTester_CorrectListIsReturned() throws Exception {
		List<PhoneSummary> phoneSummaries = phoneBookingUseCase.fetchPhonesBookedByTester("user.4");

		for (PhoneSummary phoneSummary : phoneSummaries) {
			log.info("Available phone type: {}", phoneSummary);
		}
		Assertions.assertEquals(1, phoneSummaries.size());
		Assertions.assertEquals("35f29256-6717-4898-a734-26d055899603", phoneSummaries.get(0).getPhoneId().toString());
	}

	@Test
	@Order(7)
	@Commit
	void givenTesterUnknown_whenReturningPhone_PhoneNotReturned() throws Exception {
		PhoneReturnRequest phoneReturnRequest = PhoneBookingUseCaseTestFixture.buildPhoneReturnRequestWithUnknownTester();

		PhoneReturnResult phoneReturnResult = phoneBookingUseCase.returnPhone(phoneReturnRequest);
		Assertions.assertFalse(phoneReturnResult.isSuccessful());
		Assertions.assertEquals(PhoneReturnResult.Message.TESTER_NOT_REGISTERED, phoneReturnResult.getMessage());
	}

	@Test
	@Order(8)
	@Commit
	void givenTesterAndPhoneIdDontMatch_whenReturningPhone_PhoneNotReturned() throws Exception {
		PhoneReturnRequest phoneReturnRequest = PhoneBookingUseCaseTestFixture.buildPhoneReturnRequestWithNonMatchingArguments();

		PhoneReturnResult phoneReturnResult = phoneBookingUseCase.returnPhone(phoneReturnRequest);
		Assertions.assertFalse(phoneReturnResult.isSuccessful());
		Assertions.assertEquals(PhoneReturnResult.Message.PHONE_NOT_FOUND, phoneReturnResult.getMessage());
	}

	@Test
	@Order(9)
	@Commit
	void givenPhoneIdArgumentInvalid_whenReturningPhone_PhoneNotReturned() throws Exception {
		PhoneReturnRequest phoneReturnRequest = PhoneBookingUseCaseTestFixture.buildPhoneReturnRequestWithInvalidPhoneId();

		PhoneReturnResult phoneReturnResult = phoneBookingUseCase.returnPhone(phoneReturnRequest);
		Assertions.assertFalse(phoneReturnResult.isSuccessful());
		Assertions.assertEquals(PhoneReturnResult.Message.PHONE_NOT_FOUND, phoneReturnResult.getMessage());
	}

	@Test
	@Order(10)
	@Commit
	void givenPhoneReturnRequestValid_whenReturningPhone_PhoneReturned() throws Exception {
		PhoneReturnRequest phoneReturnRequest = PhoneBookingUseCaseTestFixture.buildValidPhoneReturnRequest();

		PhoneReturnResult phoneReturnResult = phoneBookingUseCase.returnPhone(phoneReturnRequest);
		Assertions.assertTrue(phoneReturnResult.isSuccessful());
		Assertions.assertEquals(PhoneReturnResult.Message.SUCCESS, phoneReturnResult.getMessage());

		Phone returnedPhone = phones.findById(new Phone.PhoneId(phoneReturnResult.getPhoneId())).get();
		Assertions.assertTrue(returnedPhone.isAvailable());
	}
}
