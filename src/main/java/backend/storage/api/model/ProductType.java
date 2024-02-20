package backend.storage.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "product_types")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    public ProductType(Type type) {
        this.type = type;
    }
    public ProductType(String type) {
        this.type = Type.valueOf(type);
    }

    public static ProductType getByCode(int code) {
        Type[] values = Type.values();
        for (Type type : values) {
            if (type.getCode() == code) {
                return new ProductType(type);
            }
        }
        throw new IllegalArgumentException("No such Type for code: " + code);
    }

    public enum Type {
        FOOTAGE(0),
        PIECE(1),
        BOTTLING(2);

        private final int code;

        Type(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static Type getByCode(int code) {
            for (Type type : values()) {
                if (type.code == code) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No such Type for code: " + code);
        }
    }
}
