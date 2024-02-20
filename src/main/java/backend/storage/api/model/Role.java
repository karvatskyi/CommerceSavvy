package backend.storage.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.yaml.snakeyaml.events.Event;

import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@RequiredArgsConstructor
public class Role {
    public Role(String roleName) {
        this.roleName = RoleName.valueOf(roleName);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private RoleName roleName;

    public enum RoleName {
        STOREKEEPER,
        MANAGER,
        ADMIN,
        OWNER
    }
}
