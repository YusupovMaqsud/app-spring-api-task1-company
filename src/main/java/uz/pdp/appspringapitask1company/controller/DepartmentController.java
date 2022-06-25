package uz.pdp.appspringapitask1company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appspringapitask1company.entity.Company;
import uz.pdp.appspringapitask1company.entity.Department;
import uz.pdp.appspringapitask1company.payload.ApiResponse;
import uz.pdp.appspringapitask1company.payload.CompanyDto;
import uz.pdp.appspringapitask1company.payload.DepartmentDto;
import uz.pdp.appspringapitask1company.service.AddressService;
import uz.pdp.appspringapitask1company.service.CompanyService;
import uz.pdp.appspringapitask1company.service.DepartmentService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;


    /**
     * BARCHA DEPARTMENTLAR QAYTARISH
     * @return
     */
    @GetMapping("/api/department")
    public ResponseEntity<List<Department>> getDepartments(){
        List<Department> departments = departmentService.getDepartments();
        return ResponseEntity.ok(departments);
    }


    /**
     * ID ORQALI DEPARTMENT QAYTARISH
     * @param id
     * @return
     */
    @GetMapping("/api/department/{id}")
    public ResponseEntity<ApiResponse> getDepartment(@PathVariable Integer id){
        ApiResponse department = departmentService.getDepartment(id);
        return ResponseEntity.status(department.isSuccess()? HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(department);
    }



    /**
     * DEPARTMENT QOSHISH
     * @param departmentDto
     * @return
     */
    @PostMapping("/api/department")
    public ResponseEntity<ApiResponse> addDepartment(@Valid @RequestBody DepartmentDto departmentDto){
        ApiResponse apiResponse = departmentService.addDepartment(departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);

    }





    /**
     * DEPARTMENT DELETED
     * @param id
     * @return
     */
    @DeleteMapping("/api/department/{id}")
    public ResponseEntity<ApiResponse> deleteDepartment(@PathVariable Integer id){
        ApiResponse apiResponse = departmentService.deleteDepartment(id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }




    /**
     * DEPARTMENT NI EDIT QILISH
     * @param id
     * @param departmentDto
     * @return
     */
    @PutMapping("/api/department/{id}")
    public ResponseEntity<ApiResponse> editDepartment(@PathVariable Integer id,@Valid @RequestBody DepartmentDto departmentDto){
        ApiResponse apiResponse = departmentService.editDepartment(id, departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }




    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
