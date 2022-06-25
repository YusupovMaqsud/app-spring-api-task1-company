package uz.pdp.appspringapitask1company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appspringapitask1company.entity.Address;
import uz.pdp.appspringapitask1company.payload.AddressDto;
import uz.pdp.appspringapitask1company.payload.ApiResponse;
import uz.pdp.appspringapitask1company.service.AddressService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AddressController {
    @Autowired
    AddressService addressService;

    /**
     * BARCHA ADDRESSLAR
     *
     * @return addresses
     */
    @GetMapping("/api/address")
    public ResponseEntity<List<Address>> getAddresses() {
        List<Address> addresses = addressService.getAddresses();
        return ResponseEntity.ok(addresses);
    }


    /**
     * ID ORQALI ADDRESS QAYTARAMIZ
     *
     * @param id
     * @return
     */
    @GetMapping("/api/address/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable Integer id) {
        Address address = addressService.getAddress(id);
        return ResponseEntity.ok(address);
    }


    /**
     * ADDRESS QUSHADIGAN
     *
     * @param addressDto
     * @return
     */
    @PostMapping("/api/address")
    public ResponseEntity<ApiResponse> addAddress(@Valid @RequestBody AddressDto addressDto) {
        ApiResponse apiResponse = addressService.addAddress(addressDto);
        if (apiResponse.isSuccess()) {
            return ResponseEntity.status(201).body(apiResponse);

        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }


    /**
     * ADDRESS DELETED
     *
     * @param id
     * @return
     */
    @DeleteMapping("/api/address/{id}")
    public ResponseEntity<ApiResponse> deleteAddress(@PathVariable Integer id) {
        ApiResponse apiResponse = addressService.deleteAddress(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }


    /**
     * ADDRESS EDITED
     *
     * @param id
     * @param addressDto
     * @return
     */
    @PutMapping("/api/address/{id}")
    public ResponseEntity<ApiResponse> editAddress(@Valid @PathVariable Integer id, @RequestBody AddressDto addressDto) {
        ApiResponse apiResponse = addressService.editAddress(id, addressDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
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
