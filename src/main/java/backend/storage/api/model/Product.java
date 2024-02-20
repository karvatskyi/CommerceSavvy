package backend.storage.api.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE products SET is_deleted = true WHERE id =?")
@Where(clause = "is_deleted=false")
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "weight", nullable = false)
    private double weight;

    @OneToOne
    @JoinColumn(name = "product_type_id", nullable = false)
    private ProductType productType;

    @Column(name = "description")
    private String description;

    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDelete = false;
}
