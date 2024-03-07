package backend.storage.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "size_task", nullable = false)
    private int sizeTask;

    @ManyToOne
    @JoinColumn(name = "author_task_id", nullable = false)
    private Employee authorTaskId;

    @ManyToOne
    @JoinColumn(name = "performer_task_id")
    private Employee performerTaskId;

    @OneToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @OneToOne
    @JoinColumn(name = "task_type_id", nullable = false)
    private TaskType taskType;

    @OneToMany
    @JoinTable(name = "task_items",
    joinColumns = @JoinColumn(name = "task_id"),
    inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<Item> itemList;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;

    @Column(name = "start_perform_time")
    private LocalDateTime startPerformTime;

    @Column(name = "buyer")
    private String buyer;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "notes")
    private String notes;
}
