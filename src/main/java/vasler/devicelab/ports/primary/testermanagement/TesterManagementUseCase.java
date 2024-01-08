package vasler.devicelab.ports.primary.testermanagement;

import vasler.devicelab.ports.primary.testermanagement.dto.TesterDetails;

import java.util.List;

public interface TesterManagementUseCase {
    public List<TesterDetails> listRegisteredTesters() throws Exception;
}
