package vasler.devicelab.domain.model.phonetype;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;
import org.springframework.data.annotation.PersistenceCreator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Entity
@Table(name = "phone_type")
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceCreator))
public class PhoneType implements AggregateRoot<PhoneType, PhoneType.PhoneTypeId> {
    public record PhoneTypeId(@Column(name = "phone_type_id") String id) implements Identifier, Serializable { }

    @Getter
    @EmbeddedId
    private final PhoneTypeId id;

    @Getter
    private String name;

    @Version
    private Integer version;
}
