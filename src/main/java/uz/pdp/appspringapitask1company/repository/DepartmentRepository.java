package uz.pdp.appspringapitask1company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appspringapitask1company.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Integer id);
}
