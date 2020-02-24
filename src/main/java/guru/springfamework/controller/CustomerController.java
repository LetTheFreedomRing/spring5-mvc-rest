package guru.springfamework.controller;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/customers/")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    public ResponseEntity<CustomerListDTO> listCustomers() {
        return new ResponseEntity<CustomerListDTO>(new CustomerListDTO(customerService.getAll()), HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable(name = "customerId") Long customerId) {
        return new ResponseEntity<CustomerDTO>(customerService.getById(customerId), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<CustomerDTO>(customerService.create(customerDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable(name = "customerId") Long customerId, @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<CustomerDTO>(customerService.saveByDTO(customerId, customerDTO), HttpStatus.OK);
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable(name = "customerId") Long customerId, @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<CustomerDTO>(customerService.patch(customerId, customerDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable(name = "customerId") Long customerId) {
        customerService.deleteById(customerId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
