package uz.pdp.appspringapitask1company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appspringapitask1company.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker,Integer> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);
}
