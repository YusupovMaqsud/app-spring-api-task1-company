package uz.pdp.appspringapitask1company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appspringapitask1company.entity.Company;
import uz.pdp.appspringapitask1company.payload.ApiResponse;
import uz.pdp.appspringapitask1company.payload.CompanyDto;
import uz.pdp.appspringapitask1company.service.AddressService;
import uz.pdp.appspringapitask1company.service.CompanyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CompanyController {
    @Autowired
    CompanyService companyService;


    /**
     * BARCHA COMPANYLAR QAYTARISH
     * @return
     */
    @GetMapping("/api/company")
    public ResponseEntity<List<Company>> getCompanies(){
        List<Company> companies = companyService.getCompanies();
        return ResponseEntity.ok(companies);
    }


    /**
     * ID ORQALI COMPANY QAYTARISH
     * @param id
     * @return
     */
    @GetMapping("/api/company/{id}")
    public ResponseEntity<ApiResponse> getCompany(@PathVariable Integer id){
        ApiResponse company = companyService.getCompany(id);
        return ResponseEntity.status(company.isSuccess()? HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(company);
    }


    /**
     * COMPANY QOSHISH
     * @param companyDto
     * @return
     */
    @PostMapping("/api/copmany")
    public ResponseEntity<ApiResponse> addCompany(@Valid @RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.addCompany(companyDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);

    }


    /**
     * COMPANY DELETED
     * @param id
     * @return
     */
    @DeleteMapping("/api/company/{id}")
    public ResponseEntity<ApiResponse> deleteCompany(@PathVariable Integer id){
        ApiResponse apiResponse = companyService.deleteCompany(id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }


    /**
     * COMPANY NI EDIT QILISH
     * @param id
     * @param companyDto
     * @return
     */
    @PutMapping("/api/company/{id}")
    public ResponseEntity<ApiResponse> editCompany(@PathVariable Integer id,@Valid @RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.editCompany(id, companyDto);
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
