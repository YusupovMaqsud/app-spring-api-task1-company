package uz.pdp.appspringapitask1company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appspringapitask1company.entity.Company;

public interface CompanyRepository extends JpaRepository<Company,Integer> {
    boolean existsByCorpNameAndDirectorName(String corpName, String directorName);

    boolean existsByCorpNameAndDirectorNameAndIdNot(String corpName, String directorName, Integer id);
}
