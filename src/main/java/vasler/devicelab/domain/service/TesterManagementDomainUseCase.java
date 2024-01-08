package vasler.devicelab.domain.service;

import vasler.devicelab.ports.primary.testermanagement.dto.TesterDetails;

import java.util.List;

public interface TesterManagementDomainUseCase {
    public List<TesterDetails> listRegisteredTesters() throws Exception;
}
