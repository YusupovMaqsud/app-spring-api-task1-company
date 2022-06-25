package uz.pdp.appspringapitask1company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appspringapitask1company.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Integer> {
}
