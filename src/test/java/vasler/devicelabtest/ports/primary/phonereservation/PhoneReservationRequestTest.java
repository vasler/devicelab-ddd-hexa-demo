package vasler.devicelabtest.ports.primary.phonereservation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import vasler.devicelabtest._config_.PostgresTestConfiguration;
import vasler.devicelab.ports.primary.phonereservation.PhoneReservationUseCaseException;
import vasler.devicelab.ports.primary.phonereservation.dto.PhoneReservationRequest;
import vasler.devicelabtest._config_.TestConfig;

@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(PostgresTestConfiguration.class)
public class PhoneReservationRequestTest {
    @Test
    void whenBuildingPhoneType_givenPhoneTypeArgumentIsNotNull_PhoneTypeIsReturned() {
        Assertions.assertEquals("test", PhoneReservationRequest.builder().phoneType("test").tester("tester").build().getPhoneType());
    }

    @Test
    void whenBuildingPhoneType_givenPhoneTypeArgumentIsNull_ExceptionIsThrown() {
        Assertions.assertThrows(PhoneReservationUseCaseException.class, () -> {
            PhoneReservationRequest.builder().phoneType(null).build();
        });
    }

    @Test
    void whenBuildingPhoneType_givenTesterArgumentIsNull_ExceptionIsThrown() {
        Assertions.assertThrows(PhoneReservationUseCaseException.class, () -> {
            PhoneReservationRequest.builder().phoneType("test").tester(null).build();
        });
    }
}
