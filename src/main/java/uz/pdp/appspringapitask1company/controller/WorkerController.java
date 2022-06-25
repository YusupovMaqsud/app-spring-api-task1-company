package uz.pdp.appspringapitask1company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appspringapitask1company.entity.Worker;
import uz.pdp.appspringapitask1company.payload.ApiResponse;
import uz.pdp.appspringapitask1company.payload.WorkerDto;
import uz.pdp.appspringapitask1company.service.AddressService;
import uz.pdp.appspringapitask1company.service.WorkerService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WorkerController {
    @Autowired
    WorkerService workerService;

    /**
     * BARCHA WORKERLARNI QAYTARAMIZ
     * @return workerList
     */
    @GetMapping("/api/worker")
    public ResponseEntity<List<Worker>> getWorkers(){
        List<Worker> workerList = workerService.getWorkers();
        return ResponseEntity.ok(workerList);
    }


    /**
     * ID ORQALI WORKER
     * @param id
     * @return
     */
    @GetMapping("/api/worker/{id}")
    public ResponseEntity<Worker> getWorker(@PathVariable Integer id){
        Worker worker = workerService.getWorker(id);
        return ResponseEntity.ok(worker);
    }


    /**
     * WORKER QUSHADIGAN
     * @param workerDto
     * @return
     */
    @PostMapping("/api/worker")
    public ResponseEntity<ApiResponse> addWorker(@Valid @RequestBody WorkerDto workerDto){
        ApiResponse apiResponse = workerService.addWorker(workerDto);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResponse);
    }


    /**
     * WORKERNI O'CHIRISH
     * @param id
     * @return
     */
    @DeleteMapping("/api/worker/{id}")
    public ResponseEntity<ApiResponse> deleteWorker(@PathVariable Integer id){
        ApiResponse apiResponse = workerService.deleteWorker(id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
    }


    /**
     * WORKER EDIT QILINDI
     * @param workerDto
     * @param id
     * @return
     */
    @PutMapping("/api/worker/{id}")
    public ResponseEntity<ApiResponse> editWorker(@Valid @RequestBody WorkerDto workerDto,@PathVariable Integer id){
        ApiResponse apiResponse = workerService.editWorker(workerDto, id);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(apiResponse);
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

