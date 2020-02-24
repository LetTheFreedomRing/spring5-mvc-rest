package guru.springfamework.spring5mvcrest.service;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import guru.springfamework.service.ResourceNotFoundException;
import guru.springfamework.service.VendorService;
import guru.springfamework.service.VendorServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VendorServiceIT {

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    VendorService vendorService;

    @Before
    public void setUp() throws Exception {
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        vendorService = new VendorServiceImpl(vendorRepository);
    }

    @Test
    public void patchUpdateName() throws Exception {
        Long id = getVendorIdValue();
        String updatedFirstName = "New name";
        Vendor vendor = vendorRepository.findById(id).orElseThrow(RuntimeException::new);

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(updatedFirstName);
        VendorDTO updatedVendorDTO = vendorService.patch(id, vendorDTO);

        assertEquals(vendor.getId(), updatedVendorDTO.getId());
        assertEquals(updatedFirstName, updatedVendorDTO.getName());
        assertEquals(VendorServiceImpl.VENDOR_URL_HEADER + id, updatedVendorDTO.getVendorUrl());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void patchThrowsError() throws Exception {
        vendorService.patch(1000L, new VendorDTO());
    }

    @Test
    public void deleteById() throws Exception {
        Long id = getVendorIdValue();

        int vendorsSizeBeforeDelete = vendorRepository.findAll().size();

        vendorService.deleteById(id);

        assertEquals(vendorsSizeBeforeDelete - 1, vendorRepository.findAll().size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteByIdThrowsError() throws Exception {
        int vendorsSizeBeforeDelete = vendorRepository.findAll().size();

        vendorService.deleteById((long) (vendorsSizeBeforeDelete + 1000));
    }

    private Long getVendorIdValue() {
        return vendorRepository.findAll().get(0).getId();
    }
}
