package guru.springfamework.service;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

    public static final String VENDOR_URL_HEADER = "/api/v1/vendors/";

    private final VendorRepository vendorRepository;

    private final VendorMapper vendorMapper = VendorMapper.INSTANCE;

    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public List<VendorDTO> listAll() {
        List<VendorDTO> res = new ArrayList<>();
        vendorRepository.findAll().forEach(vendor -> {
            VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
            vendorDTO.setVendorUrl(VENDOR_URL_HEADER + vendor.getId());
            res.add(vendorDTO);
        });
        return res;
    }

    @Override
    public VendorDTO getById(Long id) {
        return vendorMapper.vendorToVendorDTO(vendorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Vendor " + id + " not found")));
    }

    @Override
    public VendorDTO create(VendorDTO vendorDTO) {
        return save(vendorMapper.vendorDTOToVendor(vendorDTO));
    }

    private VendorDTO save(Vendor vendor) {
        VendorDTO savedDTO = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
        savedDTO.setVendorUrl(VENDOR_URL_HEADER + savedDTO.getId());
        return savedDTO;
    }

    @Override
    public VendorDTO saveByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendor.setId(id);

        return save(vendor);
    }

    @Override
    public VendorDTO patch(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vendor " + id + " not found"));
        if (vendorDTO.getName() != null) {
            vendor.setName(vendorDTO.getName());
        }
        VendorDTO savedDTO = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
        savedDTO.setVendorUrl(VENDOR_URL_HEADER + savedDTO.getId());
        return savedDTO;
    }

    @Override
    public void deleteById(Long id) {
        try {
            vendorRepository.deleteById(id);
        } catch (NonTransientDataAccessException ex) {
            throw new ResourceNotFoundException("Vendor " + id + " not found");
        }
    }


}
