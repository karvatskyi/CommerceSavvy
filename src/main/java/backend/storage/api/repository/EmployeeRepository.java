package backend.storage.api.repository;

import backend.storage.api.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.isPresent = true")
    int employeesInWork();

    Optional<Employee> findByEmail(String email);

    @Query("SELECT e FROM Employee e WHERE e.isPresent = true")
    List<Employee> findAllIsPresentIsTrue();
}
