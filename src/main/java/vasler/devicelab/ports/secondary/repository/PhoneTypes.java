package vasler.devicelab.ports.secondary.repository;

import org.jmolecules.spring.AssociationResolver;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vasler.devicelab.domain.model.phonetype.PhoneType;

import java.util.List;
import java.util.stream.Stream;

public interface PhoneTypes extends CrudRepository<PhoneType, PhoneType.PhoneTypeId>, AssociationResolver<PhoneType, PhoneType.PhoneTypeId> {
    @Query("SELECT pt FROM PhoneType pt WHERE pt.id IN (SELECT p.phoneType FROM Phone p WHERE p.available = true GROUP BY p.phoneType HAVING COUNT(p.phoneType) > 0)")
    List<PhoneType> findAvailablePhoneTypes();
}
