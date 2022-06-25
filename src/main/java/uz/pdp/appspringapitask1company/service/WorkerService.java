package uz.pdp.appspringapitask1company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.appspringapitask1company.entity.Address;
import uz.pdp.appspringapitask1company.entity.Department;
import uz.pdp.appspringapitask1company.entity.Worker;
import uz.pdp.appspringapitask1company.payload.ApiResponse;
import uz.pdp.appspringapitask1company.payload.WorkerDto;
import uz.pdp.appspringapitask1company.repository.AddressRepository;
import uz.pdp.appspringapitask1company.repository.DepartmentRepository;
import uz.pdp.appspringapitask1company.repository.WorkerRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    DepartmentRepository departmentRepository;


    /**
     * BARCHA WORKERLARNI QAYTARAMIZ
     * @return workerList
     */
    public List<Worker> getWorkers() {
        List<Worker> workerList = workerRepository.findAll();
        return workerList;
    }





    /**
     * ID ORQALI WORKER
     * @param id
     * @return
     */
    public Worker getWorker(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isPresent()) {
            return optionalWorker.get();
        }
        return null;

    }




    /**
     * WORKER QUSHADIGAN
     * @param workerDto
     * @return
     */
    public ApiResponse addWorker(WorkerDto workerDto) {
        boolean existsByPhoneNumber = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (existsByPhoneNumber) {
            return new ApiResponse("Bunday phoneNumberli worker mavjud", false);
        }

        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if (!optionalAddress.isPresent()) {
            return new ApiResponse("Bunday addresli worker mavjud emas", false);
        }

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent()) {
            return new ApiResponse("Bunday department mavjud emas", false);
        }

        Worker worker = new Worker();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setAddress(optionalAddress.get());
        worker.setDepartment(optionalDepartment.get());
        workerRepository.save(worker);
        return new ApiResponse("Worker saqlandi", true);
    }




    /**
     * WORKERNI O'CHIRISH
     * @param id
     * @return
     */
    public ApiResponse deleteWorker(Integer id) {
        try {
            workerRepository.deleteById(id);
            return new ApiResponse("Worker o'chirildi", true);
        } catch (Exception e) {
            return new ApiResponse("Xatolik!!!", false);
        }
    }




    /**
     * WORKER EDIT QILINDI
     * @param workerDto
     * @param id
     * @return
     */
    public ApiResponse editWorker(WorkerDto workerDto, Integer id) {
        boolean existsByPhoneNumberAndIdNot = workerRepository.existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(), id);
        if (existsByPhoneNumberAndIdNot) {
            return new ApiResponse("Bunday phoneNumber li worker mavjud", false);
        }

        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if(!optionalWorker.isPresent()){
            return new ApiResponse("Bunday worker mavjud emas", false);

        }

        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if (!optionalAddress.isPresent()) {
            return new ApiResponse("Bunday addresli worker mavjud emas", false);
        }

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent()) {
            return new ApiResponse("Bunday department mavjud emas", false);
        }

        Worker worker = optionalWorker.get();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setAddress(optionalAddress.get());
        worker.setDepartment(optionalDepartment.get());
        workerRepository.save(worker);
        return new ApiResponse("Worker edited", true);
    }
}
