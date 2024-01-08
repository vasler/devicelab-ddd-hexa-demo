package vasler.devicelab.domain.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vasler.devicelab.ports.primary.testermanagement.dto.TesterDetails;
import vasler.devicelab.ports.secondary.repository.Testers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@AllArgsConstructor
public class TesterManagementDomainService implements TesterManagementDomainUseCase {
    private final Testers testers;
    @Override
    public List<TesterDetails> listRegisteredTesters()  throws Exception {
        return StreamSupport.stream(testers.findAll().spliterator(), false)
            .map(tester -> TesterDetails.builder().testerId(tester.getId().id()).build())
            .collect(Collectors.toList());
    }
}
