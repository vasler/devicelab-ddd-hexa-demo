package vasler.devicelab.domain.model.tester;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;
import org.springframework.data.annotation.PersistenceCreator;

import java.io.Serializable;

@Slf4j
@Entity
@Table(name = "tester")
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceCreator))
public class Tester implements AggregateRoot<Tester, Tester.TesterId> {
    public record TesterId(@Column(name = "tester_id") String id) implements Identifier, Serializable { }

    @Getter
    @EmbeddedId
    private final TesterId id;

    @Version
    private Integer version;
}
