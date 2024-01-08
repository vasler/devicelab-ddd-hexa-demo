package vasler.devicelab.domain.model.phonetype;

import jakarta.persistence.Converter;
import org.jmolecules.spring.jpa.JakartaPersistenceAssociationAttributeConverter;
import org.springframework.stereotype.Component;

@Component
@Converter(autoApply = true)
public class PhoneTypeAssociationConverter extends JakartaPersistenceAssociationAttributeConverter<PhoneType, PhoneType.PhoneTypeId, String> {
    PhoneTypeAssociationConverter() {
        super(PhoneType.PhoneTypeId.class);
    }
}
