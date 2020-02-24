package guru.springfamework.spring5mvcrest.service;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.service.CustomerService;
import guru.springfamework.service.CustomerServiceImpl;
import guru.springfamework.service.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceIT {

    private static final String EXPECTED_CUSTOMER_URL_HEADER = "/api/v1/customers/";

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    public void patchCustomerUpdateFirstName() throws Exception {
        Long id = getCustomerIdValue();
        String updatedFirstName = "New name";
        Customer customer = customerRepository.findById(id).orElseThrow(RuntimeException::new);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(updatedFirstName);
        CustomerDTO updatedCustomerDTO = customerService.patch(id, customerDTO);

        assertEquals(customer.getId(), updatedCustomerDTO.getId());
        assertEquals(updatedFirstName, updatedCustomerDTO.getFirstName());
        assertEquals(customer.getLastName(), updatedCustomerDTO.getLastName());
        assertEquals(EXPECTED_CUSTOMER_URL_HEADER + id, updatedCustomerDTO.getCustomerUrl());
    }

    @Test
    public void patchCustomerUpdateLastName() throws Exception {
        Long id = getCustomerIdValue();
        String updatedLastName = "New name";
        Customer customer = customerRepository.findById(id).orElseThrow(RuntimeException::new);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastName(updatedLastName);
        CustomerDTO updatedCustomerDTO = customerService.patch(id, customerDTO);

        assertEquals(customer.getId(), updatedCustomerDTO.getId());
        assertEquals(updatedLastName, updatedCustomerDTO.getLastName());
        assertEquals(customer.getFirstName(), updatedCustomerDTO.getFirstName());
        assertEquals(EXPECTED_CUSTOMER_URL_HEADER + id, updatedCustomerDTO.getCustomerUrl());
    }

    @Test
    public void patchCustomerUpdateFistAndLastName() throws Exception {
        Long id = getCustomerIdValue();
        String updatedFirstName = "New name";
        String updateLastName = "New last name";
        Customer customer = customerRepository.findById(id).orElseThrow(RuntimeException::new);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(updatedFirstName);
        customerDTO.setLastName(updateLastName);
        CustomerDTO updatedCustomerDTO = customerService.patch(id, customerDTO);

        assertEquals(customer.getId(), updatedCustomerDTO.getId());
        assertEquals(updatedFirstName, updatedCustomerDTO.getFirstName());
        assertEquals(updateLastName, updatedCustomerDTO.getLastName());
        assertEquals(EXPECTED_CUSTOMER_URL_HEADER + id, updatedCustomerDTO.getCustomerUrl());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void patchCustomerThrowsError() throws Exception {
        customerService.patch(1000L, new CustomerDTO());
    }

    @Test
    public void deleteById() throws Exception {
        Long id = getCustomerIdValue();

        int customersSizeBeforeDelete = customerRepository.findAll().size();

        customerService.deleteById(id);

        assertEquals(customersSizeBeforeDelete - 1, customerRepository.findAll().size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteByIdThrowsError() throws Exception {
        int customersSizeBeforeDelete = customerRepository.findAll().size();

        customerService.deleteById((long) (customersSizeBeforeDelete + 1000));
    }

    private Long getCustomerIdValue() {
        return customerRepository.findAll().get(0).getId();
    }
}
