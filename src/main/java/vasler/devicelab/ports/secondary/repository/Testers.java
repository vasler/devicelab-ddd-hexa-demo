package vasler.devicelab.ports.secondary.repository;

import org.jmolecules.spring.AssociationResolver;
import org.springframework.data.repository.CrudRepository;
import vasler.devicelab.domain.model.tester.Tester;

public interface Testers extends CrudRepository<Tester, Tester.TesterId>, AssociationResolver<Tester, Tester.TesterId> {
}
