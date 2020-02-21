package guru.springfamework.service;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMER_URL_HEADER = "/api/v1/customer/";

    private final CustomerRepository customerRepository;
    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAll() {
        List<CustomerDTO> res = new ArrayList<>();
        customerRepository.findAll().forEach(customer -> {
            CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
            customerDTO.setCustomerUrl(CUSTOMER_URL_HEADER + customer.getId());
            res.add(customerDTO);
        });
        return res;
    }

    @Override
    public CustomerDTO getById(Long id) {
        return customerMapper.customerToCustomerDTO(customerRepository.findById(id).orElse(null));
    }

    @Override
    public CustomerDTO create(CustomerDTO customer) {
        return customerMapper.customerToCustomerDTO(customerRepository.save(customerMapper.customerDTOToCustomer(customer)));
    }
}
