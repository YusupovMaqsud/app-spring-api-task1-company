package uz.pdp.appspringapitask1company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.appspringapitask1company.entity.Address;
import uz.pdp.appspringapitask1company.entity.Company;
import uz.pdp.appspringapitask1company.payload.ApiResponse;
import uz.pdp.appspringapitask1company.payload.CompanyDto;
import uz.pdp.appspringapitask1company.repository.AddressRepository;
import uz.pdp.appspringapitask1company.repository.CompanyRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    AddressRepository addressRepository;


    /**
     * BARCHA COMPANYLAR QAYTARISH
     * @return
     */
    public List<Company> getCompanies() {
        List<Company> companyList = companyRepository.findAll();
        return companyList;
    }





    /**
     * ID ORQALI COMPANY QAYTARISH
     * @param id
     * @return
     */
    public ApiResponse getCompany(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent()) {
            return new ApiResponse("Bunday idli company mavjud emas", false);
        }

        return new ApiResponse("Company mavjud", true, optionalCompany.get());
    }





    /**
     * COMPANY QOSHISH
     * @param companyDto
     * @return
     */
    public ApiResponse addCompany(CompanyDto companyDto) {
        boolean existsByCorpNameAndDirectorName = companyRepository.existsByCorpNameAndDirectorName(companyDto.getCorpName(), companyDto.getDirectorName());
        if (existsByCorpNameAndDirectorName) {
            return new ApiResponse("Bunday company bor", false);
        }

        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if (optionalAddress.isPresent()) {
            return new ApiResponse("Bunday addressli company mavjud", false);
        }
        Company company = new Company();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddress(optionalAddress.get());
        companyRepository.save(company);
        return new ApiResponse("Company saqlandi", true);
    }





    /**
     * COMPANY DELETED
     * @param id
     * @return
     */
    public ApiResponse deleteCompany(Integer id) {
        try {
            companyRepository.findById(id);
            return new ApiResponse("Company deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Xatolik!!!", false);
        }
    }






    /**
     * COMPANY NI EDIT QILISH
     * @param id
     * @param companyDto
     * @return
     */
    public ApiResponse editCompany(Integer id, CompanyDto companyDto) {
        boolean existsByCorpNameAndDirectorNameAndIdNot = companyRepository.existsByCorpNameAndDirectorNameAndIdNot(companyDto.getCorpName(), companyDto.getDirectorName(), id);
        if(existsByCorpNameAndDirectorNameAndIdNot){
            return new ApiResponse("Bunday corpnameli,directornameli,idli Company mavjud",false);
        }

        Optional<Company> optionalCompany = companyRepository.findById(id);
        if(!optionalCompany.isPresent()){
            return new ApiResponse("Bunday Company mavjud emas",false);
        }

        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if (!optionalAddress.isPresent()) {
            return new ApiResponse("Bunday address mavjud emas", false);
        }

        Company company = optionalCompany.get();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddress(optionalAddress.get());
        companyRepository.save(company);
        return new ApiResponse("Company edit qilindi", true);


    }
}
