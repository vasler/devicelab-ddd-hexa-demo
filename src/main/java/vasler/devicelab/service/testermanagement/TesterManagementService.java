package vasler.devicelab.service.testermanagement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vasler.devicelab.domain.service.TesterManagementDomainUseCase;
import vasler.devicelab.ports.primary.testermanagement.TesterManagementUseCase;
import vasler.devicelab.ports.primary.testermanagement.dto.TesterDetails;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TesterManagementService implements TesterManagementUseCase {
    private final TesterManagementDomainUseCase testerManagementDomainUseCase;
    @Override
    public List<TesterDetails> listRegisteredTesters() throws Exception {
        return testerManagementDomainUseCase.listRegisteredTesters();
    }
}
