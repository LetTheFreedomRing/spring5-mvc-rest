package guru.springfamework.service;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;

import java.util.List;

public interface VendorService {

    List<VendorDTO> listAll();

    VendorDTO getById(Long id);

    VendorDTO create(VendorDTO vendorDTO);

    VendorDTO saveByDTO(Long id, VendorDTO vendorDTO);

    VendorDTO patch(Long id, VendorDTO vendorDTO);

    void deleteById(Long id);
}
