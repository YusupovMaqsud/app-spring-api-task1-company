package uz.pdp.appspringapitask1company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appspringapitask1company.entity.Address;
import uz.pdp.appspringapitask1company.entity.Company;
import uz.pdp.appspringapitask1company.entity.Department;
import uz.pdp.appspringapitask1company.payload.ApiResponse;
import uz.pdp.appspringapitask1company.payload.CompanyDto;
import uz.pdp.appspringapitask1company.payload.DepartmentDto;
import uz.pdp.appspringapitask1company.repository.AddressRepository;
import uz.pdp.appspringapitask1company.repository.CompanyRepository;
import uz.pdp.appspringapitask1company.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CompanyRepository companyRepository;



    /**
     * BARCHA DEPARTMENTLAR QAYTARISH
     * @return
     */
    public List<Department> getDepartments() {
        List<Department> departmentList = departmentRepository.findAll();
        return departmentList;
    }





    /**
     * ID ORQALI DEPARTMENT QAYTARISH
     * @param id
     * @return
     */
    public ApiResponse getDepartment(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent()) {
            return new ApiResponse("Bunday idli Department mavjud emas", false);
        }

        return new ApiResponse("Department mavjud", true, optionalDepartment.get());
    }





    /**
     * DEPARTMENT QOSHISH
     * @param departmentDto
     * @return
     */
    public ApiResponse addDepartment(DepartmentDto departmentDto) {
        boolean existsByName = departmentRepository.existsByName(departmentDto.getName());
        if (existsByName) {
            return new ApiResponse("Bunday company bor", false);
        }

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent()) {
            return new ApiResponse("Bunday  company mavjud emas", false);
        }

        Department department=new Department();
        department.setName(departmentDto.getName());
        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return new ApiResponse("Department saqlandi", true);
    }





    /**
     * DEPARTMENT DELETED
     * @param id
     * @return
     */
    public ApiResponse deleteDepartment(Integer id) {
        try {
            departmentRepository.findById(id);
            return new ApiResponse("Department deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Xatolik!!!", false);
        }
    }






    /**
     * DEPARTMENT NI EDIT QILISH
     * @param id
     * @param departmentDto
     * @return
     */
    public ApiResponse editDepartment(Integer id, DepartmentDto departmentDto) {
        boolean existsByNameAndIdNot = departmentRepository.existsByNameAndIdNot(departmentDto.getName(), id);
        if(existsByNameAndIdNot){
            return new ApiResponse("Bunday nameli va idli Department mavjud",false);
        }

        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if(!optionalDepartment.isPresent()){
            return new ApiResponse("Bunday Department mavjud emas",false);
        }

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent()) {
            return new ApiResponse("Bunday  Company mavjud emas", false);
        }

        Department department=new Department();
        department.setName(departmentDto.getName());
        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return new ApiResponse("Department edit qilindi", true);


    }
}
