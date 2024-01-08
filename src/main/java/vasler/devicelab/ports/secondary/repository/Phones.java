package vasler.devicelab.ports.secondary.repository;

import org.jmolecules.ddd.types.Association;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import vasler.devicelab.domain.model.phone.Phone;
import vasler.devicelab.domain.model.phonetype.PhoneType;
import vasler.devicelab.domain.model.tester.Tester;

import java.util.List;

public interface Phones extends CrudRepository<Phone, Phone.PhoneId> {
    List<Phone> findByPhoneType(Association<PhoneType, PhoneType.PhoneTypeId> phoneType);

    List<Phone> findByReservedBy(Association<Tester, Tester.TesterId> reservedBy);

    @Query("SELECT p FROM Phone p WHERE p.phoneType = :phoneType AND p.available = true")
    List<Phone> findAvailablePhonesByPhoneType(Association<PhoneType, PhoneType.PhoneTypeId> phoneType);
}
