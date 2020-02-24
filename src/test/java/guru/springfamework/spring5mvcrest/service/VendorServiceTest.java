package guru.springfamework.spring5mvcrest.service;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import guru.springfamework.service.VendorServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class VendorServiceTest {
    private static final Long VENDOR_ID = 1L;
    private static final String VENDOR_NAME = "Greg";

    @Mock
    VendorRepository vendorRepository;

    @InjectMocks
    VendorServiceImpl vendorService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAll() {
        Vendor vendor1 = new Vendor();
        vendor1.setId(VENDOR_ID);
        vendor1.setName(VENDOR_NAME);

        Vendor vendor2 = new Vendor();
        vendor2.setId(2L);
        vendor2.setName(VENDOR_NAME);

        List<Vendor> vendors = new ArrayList<>(Arrays.asList(vendor1, vendor2));

        Mockito.when(vendorRepository.findAll()).thenReturn(vendors);
        List<VendorDTO> vendorDTOS = vendorService.listAll();

        assertEquals(vendors.size(), vendorDTOS.size());
    }

    @Test
    public void getById() {
        Vendor vendor = new Vendor();
        vendor.setId(VENDOR_ID);

        Mockito.when(vendorRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(vendor));

        VendorDTO vendorDTO = vendorService.getById(VENDOR_ID);

        assertEquals(VENDOR_ID, vendorDTO.getId());
    }

    @Test
    public void create() {
        Vendor vendor = new Vendor();
        vendor.setId(VENDOR_ID);
        vendor.setName(VENDOR_NAME);


        Mockito.when(vendorRepository.save(ArgumentMatchers.any())).thenReturn(vendor);

        VendorDTO savedVendor = vendorService.create(new VendorDTO());
        assertEquals(VENDOR_ID, savedVendor.getId());
        assertEquals(VENDOR_NAME, savedVendor.getName());
        assertEquals(VendorServiceImpl.VENDOR_URL_HEADER + VENDOR_ID, savedVendor.getVendorUrl());
    }

    @Test
    public void saveByDTO() {
        Long updatedId = 2L;

        VendorDTO vendor = new VendorDTO();
        vendor.setName(VENDOR_NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setId(updatedId);
        savedVendor.setName(VENDOR_NAME);

        Mockito.when(vendorRepository.save(ArgumentMatchers.any())).thenReturn(savedVendor);

        VendorDTO saveVendorDTO = vendorService.saveByDTO(updatedId, vendor);
        assertEquals(updatedId, saveVendorDTO.getId());
        assertEquals(VENDOR_NAME, saveVendorDTO.getName());
        assertEquals(VendorServiceImpl.VENDOR_URL_HEADER + updatedId, saveVendorDTO.getVendorUrl());
    }

    @Test
    public void deleteById() {
        Long id = 1L;

        vendorService.deleteById(id);

        Mockito.verify(vendorRepository, Mockito.times(1)).deleteById(ArgumentMatchers.anyLong());
    }
}
