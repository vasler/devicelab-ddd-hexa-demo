package vasler.devicelab.domain.model.phone;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Association;
import org.jmolecules.ddd.types.Identifier;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import vasler.devicelab.domain.model.phonetype.PhoneType;
import vasler.devicelab.domain.model.phonetype.PhoneTypeAssociationConverter;
import vasler.devicelab.domain.model.tester.Tester;
import vasler.devicelab.domain.model.tester.TesterAssociationConverter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Entity
@Table(name = "phone")
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceCreator))
public class Phone implements AggregateRoot<Phone, Phone.PhoneId> {
    public record PhoneId(@Column(name = "phone_id") UUID id) implements Identifier, Serializable { }

    @Getter
    @EmbeddedId
    private final PhoneId id;

    @Getter
    @Column(name = "phone_type_id")
    @Convert(converter = PhoneTypeAssociationConverter.class)
    Association<PhoneType, PhoneType.PhoneTypeId> phoneType;

    @Getter
    private boolean available;
    @Getter
    private LocalDateTime reservedOn;

    @Getter
    @Convert(converter = TesterAssociationConverter.class)
    Association<Tester, Tester.TesterId> reservedBy;

    @Version
    private Integer version;

    public static class PhoneBuilder {
        public PhoneBuilder phoneType(String phoneTypeId) {
            this.phoneType = Association.forId(new PhoneType.PhoneTypeId(phoneTypeId));
            return this;
        }

        public PhoneBuilder phoneType(Association<PhoneType, PhoneType.PhoneTypeId> phoneType) {
            this.phoneType = phoneType;
            return this;
        }
    }

    // DOMAIN METHODS
    public boolean reservePhone(Tester tester) {
        if (!available) return false;

        available = false;
        reservedOn = LocalDateTime.now();
        reservedBy = Association.forAggregate(tester);

        return true;
    }

    public boolean returnPhone(Tester tester) {
        if (available || !reservedBy.pointsTo(tester)) return false;

        available = true;
        reservedOn = null;
        reservedBy = null;

        return true;
    }
}
