package vasler.devicelab.ports.primary.testermanagement;

import org.junit.jupiter.api.*;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import vasler.devicelab._config_.PostgresTestConfiguration;
import vasler.devicelab.domain.model.phone.Phone;
import vasler.devicelab.ports.primary.phonereservation.PhoneRegistrationUseCaseTestFixture;
import vasler.devicelab.ports.primary.phonereservation.PhoneReservationUseCase;
import vasler.devicelab.ports.primary.phonereservation.dto.*;
import vasler.devicelab.ports.primary.testermanagement.dto.TesterDetails;
import vasler.devicelab.ports.secondary.repository.PhoneTypes;
import vasler.devicelab.ports.secondary.repository.Phones;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(PostgresTestConfiguration.class)
class TesterManagementUseCaseTest {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(TesterManagementUseCaseTest.class);

	@SpyBean
	private TesterManagementUseCase testerManagementUseCase;

	@Test
	@Order(1)
	@Commit
	void whenListingRegisteredTesters_CorrectListReturned() throws Exception {
		List<TesterDetails> testerDetailsList = testerManagementUseCase.listRegisteredTesters();

		Set<String> expectedTesters = Stream.iterate(1, e -> e < 11, e -> e + 1)
			.map("user.%d"::formatted)
			.collect(Collectors.toSet());

		Set<String> retrievedTesters = testerDetailsList.stream()
			.map(TesterDetails::getTesterId)
			.collect(Collectors.toSet());

		Assertions.assertEquals(expectedTesters.size(), retrievedTesters.size());
		Assertions.assertTrue(retrievedTesters.containsAll(expectedTesters));
		Assertions.assertTrue(expectedTesters.containsAll(retrievedTesters));
	}
}
