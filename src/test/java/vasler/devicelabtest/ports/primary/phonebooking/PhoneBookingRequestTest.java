package vasler.devicelabtest.ports.primary.phonebooking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import vasler.devicelabtest._config_.PostgresTestConfiguration;
import vasler.devicelab.ports.primary.phonebooking.PhoneBookingUseCaseException;
import vasler.devicelab.ports.primary.phonebooking.dto.PhoneBookingRequest;
import vasler.devicelabtest._config_.TestConfig;

@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(PostgresTestConfiguration.class)
public class PhoneBookingRequestTest {
    @Test
    void whenBuildingPhoneType_givenPhoneTypeArgumentIsNotNull_PhoneTypeIsReturned() {
        Assertions.assertEquals("test", PhoneBookingRequest.builder().phoneType("test").tester("tester").build().getPhoneType());
    }

    @Test
    void whenBuildingPhoneType_givenPhoneTypeArgumentIsNull_ExceptionIsThrown() {
        Assertions.assertThrows(PhoneBookingUseCaseException.class, () -> {
            PhoneBookingRequest.builder().phoneType(null).build();
        });
    }

    @Test
    void whenBuildingPhoneType_givenTesterArgumentIsNull_ExceptionIsThrown() {
        Assertions.assertThrows(PhoneBookingUseCaseException.class, () -> {
            PhoneBookingRequest.builder().phoneType("test").tester(null).build();
        });
    }
}
