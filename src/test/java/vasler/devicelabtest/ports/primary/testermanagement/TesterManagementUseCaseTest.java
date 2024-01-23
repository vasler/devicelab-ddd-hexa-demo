package vasler.devicelabtest.ports.primary.testermanagement;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import vasler.devicelabtest._config_.PostgresTestConfiguration;
import vasler.devicelab.ports.primary.testermanagement.TesterManagementUseCase;
import vasler.devicelab.ports.primary.testermanagement.dto.TesterDetails;
import vasler.devicelabtest._config_.TestConfig;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
