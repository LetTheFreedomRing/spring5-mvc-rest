package guru.springfamework.spring5mvcrest.service;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.service.CustomerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class CustomerServiceTest {

    private static final Long CUSTOMER_ID = 1L;
    private static final String CUSTOMER_FIRST_NAME = "Greg";
    private static final String CUSTOMER_LAST_NAME = "House";

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerServiceImpl customerService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAll() {
        Customer customer1 = new Customer();
        customer1.setId(CUSTOMER_ID);
        customer1.setFirstName(CUSTOMER_FIRST_NAME);
        customer1.setLastName(CUSTOMER_LAST_NAME);

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName(CUSTOMER_FIRST_NAME);
        customer2.setLastName(CUSTOMER_LAST_NAME);

        List<Customer> customers = new ArrayList<>(Arrays.asList(customer1, customer2));

        Mockito.when(customerRepository.findAll()).thenReturn(customers);
        List<CustomerDTO> customerDTOS = customerService.getAll();

        assertEquals(customers.size(), customerDTOS.size());
    }

    @Test
    public void getById() {
        Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);

        Mockito.when(customerRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(customer));

        CustomerDTO customerDTO = customerService.getById(CUSTOMER_ID);

        assertEquals(CUSTOMER_ID, customerDTO.getId());
    }

    @Test
    public void create() {
        Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);
        customer.setFirstName(CUSTOMER_FIRST_NAME);
        customer.setLastName(CUSTOMER_LAST_NAME);


        Mockito.when(customerRepository.save(ArgumentMatchers.any())).thenReturn(customer);

        CustomerDTO savedCustomer = customerService.create(new CustomerDTO());
        assertEquals(CUSTOMER_ID, savedCustomer.getId());
        assertEquals(CUSTOMER_FIRST_NAME, savedCustomer.getFirstName());
        assertEquals(CUSTOMER_LAST_NAME, savedCustomer.getLastName());
        assertEquals(CustomerServiceImpl.CUSTOMER_URL_HEADER + CUSTOMER_ID, savedCustomer.getCustomerUrl());
    }

    @Test
    public void saveByDTO() {
        Long updatedId = 2L;

        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName(CUSTOMER_FIRST_NAME);
        customer.setLastName(CUSTOMER_LAST_NAME);

        Customer savedCustomer = new Customer();
        savedCustomer.setId(updatedId);
        savedCustomer.setFirstName(CUSTOMER_FIRST_NAME);
        savedCustomer.setLastName(CUSTOMER_LAST_NAME);

        Mockito.when(customerRepository.save(ArgumentMatchers.any())).thenReturn(savedCustomer);

        CustomerDTO savedCustomerDTO = customerService.saveByDTO(updatedId, customer);
        assertEquals(updatedId, savedCustomerDTO.getId());
        assertEquals(CUSTOMER_FIRST_NAME, savedCustomerDTO.getFirstName());
        assertEquals(CUSTOMER_LAST_NAME, savedCustomerDTO.getLastName());
        assertEquals(CustomerServiceImpl.CUSTOMER_URL_HEADER + updatedId, savedCustomerDTO.getCustomerUrl());
    }

    @Test
    public void deleteById() {
        Long id = 1L;

        customerService.deleteById(id);

        Mockito.verify(customerRepository, Mockito.times(1)).deleteById(ArgumentMatchers.anyLong());
    }
}
