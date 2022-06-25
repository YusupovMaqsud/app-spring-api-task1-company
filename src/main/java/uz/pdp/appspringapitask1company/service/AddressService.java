package uz.pdp.appspringapitask1company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.appspringapitask1company.entity.Address;
import uz.pdp.appspringapitask1company.payload.AddressDto;
import uz.pdp.appspringapitask1company.payload.ApiResponse;
import uz.pdp.appspringapitask1company.repository.AddressRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    /**
     * BARCHA ADDRESSLAR
     * @return addresses
     */
    public List<Address> getAddresses(){
        List<Address> addressList = addressRepository.findAll();
        return addressList;
    }




    /**
     * ID ORQALI ADDRESS QAYTARAMIZ
     * @param id
     * @return
     */
    public Address getAddress(Integer id){
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if(optionalAddress.isPresent()){
            return optionalAddress.get();
        }
        return null;
    }



    /**
     *ADDRESS QUSHADIGAN
     * @param addressDto
     * @return
     */
    public ApiResponse addAddress( AddressDto addressDto){
        Address address=new Address();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("Address saqlandi",true);
    }





    /**
     * ADDRESS DELETED
     * @param id
     * @return
     */
    public ApiResponse deleteAddress(@PathVariable Integer id){
        try {
        addressRepository.deleteById(id);
            return new ApiResponse("Address o'chirildi",true);
        }catch (Exception e){
            return new ApiResponse("Xatolik!!!",false);
        }

    }





    /**
     * ADDRESS EDITED
     * @param id
     * @param addressDto
     * @return
     */
    public ApiResponse editAddress(@PathVariable Integer id ,@RequestBody AddressDto addressDto){
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if(!optionalAddress.isPresent()){
           return new ApiResponse("Bunday address mavjud emas",false);
        }
        Address address = optionalAddress.get();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("Address edit qilindi",true);
    }
}
