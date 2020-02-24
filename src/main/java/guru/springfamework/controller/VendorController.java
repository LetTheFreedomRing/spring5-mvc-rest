package guru.springfamework.controller;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.service.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/vendors/")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO listVendors() {
        return new VendorListDTO(vendorService.listAll());
    }

    @GetMapping("/{vendorId}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendor(@PathVariable(name = "vendorId") Long vendorId) {
        return vendorService.getById(vendorId);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createVendor(@RequestBody VendorDTO vendorDTO) {
        return vendorService.create(vendorDTO);
    }

    @DeleteMapping("/{vendorId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable(name = "vendorId") Long vendorId) {
        vendorService.deleteById(vendorId);
    }

    @PutMapping("/{vendorId}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@PathVariable(name = "vendorId") Long vendorId, @RequestBody VendorDTO vendorDTO) {
        return vendorService.saveByDTO(vendorId, vendorDTO);
    }

    @PatchMapping("/{vendorId}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@PathVariable(name = "vendorId") Long vendorId, @RequestBody VendorDTO vendorDTO) {
        return vendorService.patch(vendorId, vendorDTO);
    }
}
