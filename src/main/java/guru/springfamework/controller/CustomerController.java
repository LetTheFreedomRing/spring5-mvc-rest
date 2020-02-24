package guru.springfamework.controller;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers/")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDTO listCustomers() {
        return new CustomerListDTO(customerService.getAll());
    }

    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomer(@PathVariable(name = "customerId") Long customerId) {
        return customerService.getById(customerId);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.create(customerDTO);
    }

    @PutMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@PathVariable(name = "customerId") Long customerId, @RequestBody CustomerDTO customerDTO) {
        return customerService.saveByDTO(customerId, customerDTO);
    }

    @PatchMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO patchCustomer(@PathVariable(name = "customerId") Long customerId, @RequestBody CustomerDTO customerDTO) {
        return customerService.patch(customerId, customerDTO);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable(name = "customerId") Long customerId) {
        customerService.deleteById(customerId);
    }
}
