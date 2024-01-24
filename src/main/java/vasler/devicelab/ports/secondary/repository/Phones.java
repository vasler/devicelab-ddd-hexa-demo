package vasler.devicelab.ports.secondary.repository;

import org.jmolecules.ddd.types.Association;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vasler.devicelab.domain.model.phone.Phone;
import vasler.devicelab.domain.model.phonetype.PhoneType;
import vasler.devicelab.domain.model.tester.Tester;

import java.util.List;

public interface Phones extends CrudRepository<Phone, Phone.PhoneId> {
    List<Phone> findByPhoneType(Association<PhoneType, PhoneType.PhoneTypeId> phoneType);

    List<Phone> findByBookedBy(Association<Tester, Tester.TesterId> bookedBy);

    @Query("SELECT p FROM Phone p WHERE p.phoneType = :phoneType AND p.available = true")
    List<Phone> findAvailablePhonesByPhoneType(Association<PhoneType, PhoneType.PhoneTypeId> phoneType);
}
