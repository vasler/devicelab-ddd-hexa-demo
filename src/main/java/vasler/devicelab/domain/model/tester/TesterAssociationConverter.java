package vasler.devicelab.domain.model.tester;

import jakarta.persistence.Converter;
import org.jmolecules.spring.jpa.JakartaPersistenceAssociationAttributeConverter;
import org.springframework.stereotype.Component;

@Component
@Converter(autoApply = true)
public class TesterAssociationConverter extends JakartaPersistenceAssociationAttributeConverter<Tester, Tester.TesterId, String> {
    TesterAssociationConverter() {
        super(Tester.TesterId.class);
    }
}
