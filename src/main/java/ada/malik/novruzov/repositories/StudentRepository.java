package ada.malik.novruzov.repositories;

import ada.malik.novruzov.domain.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student,Long> {
}
